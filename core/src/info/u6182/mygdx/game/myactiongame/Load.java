/**
 * @file Load.java
 * @brief セーブデータの読み込みを管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import info.u6182.api.Utiility;

/**
 * @class Load
 * @brief セーブデータの読み込みを管理するクラス
 */
public class Load {

    //! 自身のインスタンス
    private static final Load LOAD = new Load();

    /**
     * @brief コンストラクタ
     */
    private Load(){
        ;
    }

    /**
     * @brief 秘密鍵の読み込みを行う
     * @return SecretKey 読み込んだ秘密鍵
     */
    public SecretKey keyLoad(){

        SecretKey key = null;

        String localPath = Gdx.files.getLocalStoragePath();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(localPath + "Save/KeySave.dat"))){

            key = (SecretKey)ois.readObject();

        }catch (Exception e){

            e.printStackTrace();

        }

        return key;

    }

    /**
     * @brief 初期化ベクトルの読み込みを行う
     * @return IvParameterSpec 読み込んだ初期化ベクトル
     */
    public IvParameterSpec ivLoad(){

        FileHandle fileHandle = Gdx.files.local("Save/IvSave.dat");

        if(!fileHandle.exists()){

            return null;

        }

        return new IvParameterSpec(fileHandle.readBytes());

    }

    /**
     * @brief 設定の読み込みを行う
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @return Settings 読み込んだ設定
     */
    public Settings settingsLoad(SecretKey key, IvParameterSpec iv){

        Settings settings = null;

        FileHandle fileHandle = Gdx.files.local("Save/SettingSave.dat");

        if(fileHandle.exists()){

            //暗号化設定バイナリ
            byte[] binary = fileHandle.readBytes();

            //復号化設定バイナリ
            byte[] decryption = Utiility.decryption(key, iv, binary);

            if(decryption == null){

                return null;

            }

            //復号化設定バイナリの保存
            fileHandle.writeBytes(decryption, false);

        }

        String localPath = Gdx.files.getLocalStoragePath();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(localPath + "Save/SettingSave.dat"))){

            settings = (Settings) ois.readObject();

        }catch (Exception e){

            e.printStackTrace();

        }

        return settings;

    }

    /**
     * @brief 最高クリアステージ番号の読み込みを行う
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @return int 読み込んだ最高クリアステージ番号
     */
    public int highClearStageNoLoad(SecretKey key, IvParameterSpec iv){

        FileHandle fileHandle = Gdx.files.local("Save/HighClearStageNoSave.dat");

        byte[] decryption = null;

        if(fileHandle.exists()){

            decryption = fileHandle.readBytes();

        }

        int highClearStageNo = 0;

        if(decryption != null){

            highClearStageNo = Utiility.decryptionInt(key, iv, decryption);

        }

        return highClearStageNo;

    }

    /**
     * @brief 最高名声スコアの読み込みを行う
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @return int 読み込んだ最高名声スコア
     */
    public int highFameScoreLoad(SecretKey key, IvParameterSpec iv){

        FileHandle fileHandle = Gdx.files.local("Save/HighFameScoreSave.dat");

        byte[] decryption = null;

        if(fileHandle.exists()){

            decryption = fileHandle.readBytes();

        }

        int highFameScore = 0;

        if(decryption != null){

            highFameScore = Utiility.decryptionInt(key, iv, decryption);

        }

        return highFameScore;

    }

    /**
     * @brief 合計名声スコアの読み込みを行う
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @return int 読み込んだ合計名声スコア
     */
    public int sumFameScoreLoad(SecretKey key, IvParameterSpec iv){

        int sumFameScore = 0;

        int[] stageFameScore = GameElement.getGameElement().getStageFameScore();

        for(int fameScore : stageFameScore){

            sumFameScore += fameScore;

        }

        return sumFameScore;

    }

    /**
     * @brief 特定ステージ以外の合計名声スコアの読み込みを行う
     * @param stageNo ステージ番号
     * @return int 読み込んだ合計名声スコア
     */
    public int gameFameScoreLoad(int stageNo){

        int sumFameScore = 0;

        int[] stageFameScore = GameElement.getGameElement().getStageFameScore();

        for(int i = 0;i < stageFameScore.length;i++){

            if(i == stageNo){

                continue;

            }

            sumFameScore += stageFameScore[i];

        }

        return sumFameScore;

    }

    /**
     * @brief Loadクラスのインスタンスを取得を行う
     * @return Load クラスのインスタンス
     */
    public static Load getLoad(){

        return LOAD;

    }

}
