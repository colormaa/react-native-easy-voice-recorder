
package com.crustaltoasttest;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.content.Context;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import java.io.IOException;
import java.util.UUID;

public class RNCrustalToastTestModule extends ReactContextBaseJavaModule {
  private MediaRecorder mediaRecorder;
  private MediaPlayer mediaPlayer;
  public static String pathSave="";
  private final ReactApplicationContext reactContext;
  Boolean record=false;
  Boolean playing = false;
  final int REQUEST_PERMISSION_CODE = 1000;

  public RNCrustalToastTestModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }
  @ReactMethod 
  public String getPath(){
    return pathSave;
  }
  @ReactMethod
  public String getRecord(){
    return "test value";
  }
  @ReactMethod
  public Boolean getPlaying(){
    return playing;
  }
  @ReactMethod
  public void show(String text) {
      Context context = getReactApplicationContext();
      Toast.makeText(context, text, Toast.LENGTH_LONG).show();
  }
  @ReactMethod 
  public void RecordStart(Callback successCallback, Callback errorCallback){
    Context context = getReactApplicationContext();
      try{
          pathSave= Environment.getExternalStorageDirectory()
                  .getAbsolutePath()+"/"
                  + UUID.randomUUID().toString()+"_audio_record.3gp";
          setupMediaRecorder(pathSave);
          mediaRecorder.prepare();
          mediaRecorder.start();
          record=true;
          playing=false;
          Toast.makeText(context, "Recording ..."+pathSave, Toast.LENGTH_SHORT).show();
          successCallback.invoke(pathSave);
      }
      catch (IOException e) {
        errorCallback.invoke(e.getMessage());
        Toast.makeText(context, "can not record ..."+pathSave, Toast.LENGTH_SHORT).show();
      }
      
   
  }
  @ReactMethod 
  public void RecordStop(){
    //if(checkPermissionFromDevice()){
      if(record==true){
        mediaRecorder.stop();
        record=false;
        playing=false;
      }
      Context context = getReactApplicationContext();
      Toast.makeText(context, "Recording stopped.", Toast.LENGTH_SHORT).show();
    // }else{
    //   requestPermission();
    // }
  }
  @ReactMethod 
  public void PlayStart(){
    //if(checkPermissionFromDevice()){
      mediaPlayer = new MediaPlayer();
      Context context = getReactApplicationContext();
      try{
          mediaPlayer.setDataSource(pathSave);
          mediaPlayer.prepare();
          mediaPlayer.start();
          record=false;
          playing=true;
          
          Toast.makeText(context, "Player started.", Toast.LENGTH_SHORT).show();
      }catch(IOException e){
          e.printStackTrace();
          Toast.makeText(context, "can not play", Toast.LENGTH_SHORT).show();
      }
      
    // }else{
    //   requestPermission();
    // }
  }
  @ReactMethod 
  public void PlayStop(String path){
    //if(checkPermissionFromDevice()){
      if(mediaPlayer !=null){
          mediaPlayer.stop();
          playing=false;
          record=false;
          mediaPlayer.release();
          setupMediaRecorder(pathSave);
      }
      Context context = getReactApplicationContext();
      Toast.makeText(context, "Player stopped.", Toast.LENGTH_SHORT).show();
    // }else{
    //   requestPermission();
    // }
  }


  @Override
  public String getName() {
    return "RNCrustalToastTest";
  }
  private void setupMediaRecorder(String path){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(path);
    }
  //   private void requestPermission(){
  //     Context context = getReactApplicationContext();
  //       ActivityCompat.requestPermissions(context, new String[]{
  //           Manifest.permission.WRITE_EXTERNAL_STORAGE,
  //               Manifest.permission.RECORD_AUDIO
  //       }, REQUEST_PERMISSION_CODE);
  //   }
  //   private boolean checkPermissionFromDevice(){
  //     Context context = getReactApplicationContext();
  //       int write_external_storage_result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
  //       int record_audio_result=ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
  //       return write_external_storage_result== PackageManager.PERMISSION_GRANTED &&
  //               record_audio_result == PackageManager.PERMISSION_GRANTED;
  //   }
  //   public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
  //     Context context = getReactApplicationContext();
  //       switch(requestCode){
  //           case REQUEST_PERMISSION_CODE:{
  //               if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)

  //                   Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
  //               else
  //                   Toast.makeText(context,"Permission denied", Toast.LENGTH_SHORT).show();
  //           }break;
  //       }
  //   }
}