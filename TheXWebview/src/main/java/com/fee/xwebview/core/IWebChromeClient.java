package com.fee.xwebview.core;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 18:04<br>
 * <P>DESC:
 * 关于 WebView的 WebChromeClient行为接口化
 * </p>
 * ******************(^_^)***********************
 */
public interface IWebChromeClient {
    int TYPE_ON_JS_ALERT = 10;

    int TYPE_ON_JS_PROMPT = TYPE_ON_JS_ALERT + 1;

    int TYPE_ON_JS_CONFIRM = TYPE_ON_JS_PROMPT + 1;

    /**
     * Tell the host application the current progress of loading a page.
     * <p>
     * //     * @param view        The WebView that initiated the callback.
     *
     * @param newProgress Current page loading progress, represented by
     */
    void onProgressChanged(int newProgress);

    /**
     * Notify the host application of a change in the document title.
     *
     * @param title A String containing the new title of the document.
     */
    void onReceivedTitle(String title);

    /**
     * Notify the host application of a new favicon for the current page.
     * <p>
     * //     * @param view The WebView that initiated the callback.
     *
     * @param icon A Bitmap containing the favicon for the current page.
     */
    void onReceivedIcon(Bitmap icon);

    /**
     * WebView在加载网页过程中Js的行为
     *
     * @param jsShowCase   <ul>
     *                     <li>{{@link #TYPE_ON_JS_ALERT}}</li>
     *                     <li>{{@link #TYPE_ON_JS_CONFIRM}}</li>
     *                     <li>{{@link #TYPE_ON_JS_PROMPT}}</li>
     *                     </ul>
     * @param url
     * @param message
     * @param defaultValue Alert时没有该值
     * @return boolean Whether the client will handle the alert/confirm/prompt dialog.
     */
    boolean onJsCase(int jsShowCase, String url, String message, String defaultValue);


    /**
     * Notify the host application that web content is requesting permission to
     * access the specified resources and the permission currently isn't granted
     * or denied. The host application must invoke {@link PermissionRequest#grant(String[])}
     * or {@link PermissionRequest#deny()}.
     * <p>
     * If this method isn't overridden, the permission is denied.
     *
     * @param request the PermissionRequest from current web content.
     */
//    void onPermissionRequest(PermissionRequest request);

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     *
     * @param message Object containing details of the console message.
     * @return true if the message is handled by the client.
     */
    boolean onConsoleMessage(String message);


    /**
     * Tell the client to open a file chooser.
     *
     * @param uploadFile A ValueCallback to set the URI of the file to upload.
     *                   onReceiveValue must be called to wake up the thread.a
     * @param acceptType The value of the 'accept' attribute of the input tag
     *                   associated with this file picker.
     * @param capture    The value of the 'capture' attribute of the input tag
     *                   associated with this file picker.
     */
    void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture);

    /**
     * Tell the client to open a file chooser.
     *
     * @param uploadFile A ValueCallback to set the URI of the file to upload.
     *                   onReceiveValue must be called to wake up the thread.a
     * @param acceptType The value of the 'accept' attribute of the input tag
     *                   associated with this file picker.
     * @param capture    The value of the 'capture' attribute of the input tag
     *                   associated with this file picker.
     */
    boolean onShowOpenFileChooser(ValueCallback<Uri[]> uploadFile, String[] acceptType, String capture);
}
