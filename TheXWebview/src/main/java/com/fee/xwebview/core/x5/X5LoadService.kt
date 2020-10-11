package com.fee.xwebview.core.x5

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.fee.xwebview.L
import com.fee.xwebview.XWebViewHelper
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 11:05<br>
 * <P>DESC:
 * X5启动的服务，在独立进程中进行
 * </p>
 * ******************(^_^)***********************
 */
class X5LoadService : Service(), PreInitCallback {
    private val TAG = "X5LoadService"

    /**
     * 当前初始化 X5的次数
     */
    private var initX5Times = 0

    private var isCanDownloadX6WithoutWifi = true

    private var isNeedBroadcastInitResult = false

    private var isCanDoInitWork = true
    override fun onCreate() {
        super.onCreate()
        L.e(TAG, "--> onCreate()")
        /**
         * TBS内核首次使用和加载时，ART虚拟机会将Dex文件转为Oat，该过程由系统底层触发且耗时较长，很容易引起anr问题，
         * 解决方法是使用TBS的 ”dex2oat优化方案“。
         */
        val configMap = mapOf<String, Any>(
            TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
            TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true
        )
        QbSdk.initTbsSettings(configMap)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        L.e(
            TAG,
            "--> onStartCommand() intent = $intent, flags = $flags, startId = $startId, isCanDoInitWork = $isCanDoInitWork"
        )
        handleIntent(intent)
        return START_NOT_STICKY
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            isNeedBroadcastInitResult =
                it.getBooleanExtra(XWebViewHelper.INTENT_KEY_NEED_BROADCAST_INIT_RESULT, false)
            isCanDownloadX6WithoutWifi =
                it.getBooleanExtra(XWebViewHelper.INTENT_KEY_CAN_DOWNLOAD_X5_WITHOUT_WIFI, true)
            if (isCanDoInitWork) {
                initX5Module()
            }
        }
    }
    private fun initX5Module() {
        isCanDoInitWork = false
        initX5Times++ //自动调多次初始化，不回调结果??
        QbSdk.setDownloadWithoutWifi(isCanDownloadX6WithoutWifi)//非 Wifi情况下
        try {
            L.e(TAG, "-------------------> start to init X5 environment initX5Times = $initX5Times")
            QbSdk.initX5Environment(application, this)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //******** PreInitCallback methods @start **********
    override fun onCoreInitFinished() {//call back step 1 回调在主线程
        //call back step 1 回调在主线程
        val x5MiniVer = QbSdk.getMiniQBVersion(applicationContext)
        L.e(TAG, "--> onCoreInitFinished()  x5MiniVer = $x5MiniVer")
    }

    override fun onViewInitFinished(isInitOk: Boolean) {//call back step 2
        isCanDoInitWork = true
        val x5MinVer = QbSdk.getMiniQBVersion(applicationContext)
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        L.e(
            TAG, "--> onViewInitFinished() isInitOk = $isInitOk, x5MinVer = $x5MinVer, tbsVer: ${
                QbSdk.getTbsVersion(
                    applicationContext
                )
            }"
        )
        try {
            val startIntent = Intent(applicationContext, ReceiveX5ResultService::class.java)
            startIntent.putExtra(XWebViewHelper.INTENT_KEY_INIT_X5_RESLUT, isInitOk)
            startService(startIntent)
            L.e(TAG, "--> onViewInitFinished() to start main process service")
            if (isNeedBroadcastInitResult) {
                //结果广播出去
                val broadcastIntent = Intent(XWebViewHelper.BROADCAST_ACTION_OF_INIT_X5_RESULT)
                broadcastIntent.putExtra(XWebViewHelper.INTENT_KEY_INIT_X5_RESLUT, isInitOk)
                sendBroadcast(broadcastIntent)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        L.e(TAG, "--> onDestroy()")
    }
}