package com.reminisense.featherq.kiosk.print.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

import com.reminisense.featherq.kiosk.print.bean.QueueDetails;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Nico on 3/1/2016.
 */
public class PrintNumberAdapter extends PrintDocumentAdapter {
    private Context context;
    private int pageHeight;
    private int pageWidth;
    private PdfDocument myPdfDocument;
    private QueueDetails queueDetails;

    public PrintNumberAdapter(Context context, QueueDetails queueDetails) {
        this.context = context;
        this.queueDetails = queueDetails;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {

        myPdfDocument = new PrintedPdfDocument(context, newAttributes);
        pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
        pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                .Builder("queue_ticket.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1);

        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {

        PageInfo newPage = new PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = myPdfDocument.startPage(newPage);
        if (cancellationSignal.isCanceled()) {
            callback.onWriteCancelled();
            myPdfDocument.close();
            myPdfDocument = null;
            return;
        }

        drawPage(page);
        myPdfDocument.finishPage(page);

        try {
            myPdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);
    }

    private void drawPage(PdfDocument.Page page) {
        Canvas canvas = page.getCanvas();
        int titleBaseLine = 72;
        int leftMargin = 54;

        // TODO design print page here!!
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        canvas.drawText("Assigned number:" + queueDetails.getAssignedNumber(), leftMargin, titleBaseLine, paint);
        canvas.drawText("Service name: " + queueDetails.getServiceName(), leftMargin, titleBaseLine + 40, paint);

    }
}
