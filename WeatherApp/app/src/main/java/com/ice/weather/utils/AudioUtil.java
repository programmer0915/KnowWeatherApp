package com.ice.weather.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.ice.weather.common.Constants;
import com.iflytek.cloud.*;

/**
 *语音播放工具类
 */
public class AudioUtil {

    // 发音人
    public final static String[] COLOUD_VOICERS_VALUE = {"xiaoyan", "xiaoxue","xiaoyu", "catherine", "henry", "vimary",
            "vixy", "xiaoqi", "vixf", "xiaomei","xiaolin", "xiaorong", "xiaoqian", "xiaokun", "xiaoqiang",
            "vixying", "xiaoxin", "nannan", "vils",};
    //标签
    private static final String TAG = "WeatherApp";
    //语音合成对象
    private static SpeechSynthesizer mTts;
    //上下文
    private  Context mContext;

    private volatile static AudioUtil instance;
    /**
     * 合成回调监听
     */
    private static SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            Log.d(TAG, "开始播放");
        }

        // TODO 缓冲的进度
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            Log.d(TAG, "缓冲 : " + i);
        }

        @Override
        public void onSpeakPaused() {
            Log.d(TAG, "暂停播放");

        }

        @Override
        public void onSpeakResumed() {
            Log.d(TAG, "继续播放");
        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            // TODO 说话的进度
            Log.d(TAG, "合成 : " + i);
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                Log.d(TAG, "播放完成");

            } else if (speechError != null) {
                Log.d(TAG, speechError.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
        /**
         * 构造方法
         *
         * @param context 上下文
         */
        private AudioUtil(Context context) {
            mContext = context;
            // 初始化合成对象
            mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        Log.d("fjj", "初始化失败,错误码：" + code);
                    }
                    Log.d("fjj", "初始化失败,q错误码：" + code);
                }
            });
        }
        public static AudioUtil getInstance(Context context) {
            if (instance == null) {
                synchronized (AudioUtil.class) {
                    if (instance == null) {
                        instance = new AudioUtil(context);
                    }
                }
            }
            return  instance;
        }
        /**
         * 停止语音播报
         */
        public static void stopSpeaking() {
            // 对象非空并且正在说话
            if (null != mTts && mTts.isSpeaking()) {
                // 停止说话
                mTts.stopSpeaking();
            }
        }
        /**
         * 判断当前有没有说话
         *
         * @return
         */
        public  static boolean isSpeaking() {
            if (null != mTts) {
                return mTts.isSpeaking();
            } else {
                return false;
            }
        }
        /**
         * 开始合成
         *
         * @param text
         */
        public void speaking(String text) {
            if (TextUtils.isEmpty(text))
                return;
            int code = mTts.startSpeaking(text, mTtsListener);

            Log.d("fjj", "-----" + code + "++++++++++");

            if (code != ErrorCode.SUCCESS) {
                if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                    Toast.makeText(mContext, "没有安装语音+ code = " + code, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
                }
            }
        }
        /**
         * 参数设置
         *
         * @return
         */
        private void setParam() {
            // 清空参数
            mTts.setParameter(SpeechConstant.PARAMS, null);
            // 引擎类型 网络
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, COLOUD_VOICERS_VALUE[1]);
            // 设置语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            // 设置音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            // 设置音量
            mTts.setParameter(SpeechConstant.VOLUME, "100");
            // 设置播放器音频流类型
            mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
            // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/KRobot/wavaudio.pcm");
            // 背景音乐  1有 0 无
            //mTts.setParameter("bgs", "1");
        }
    }
