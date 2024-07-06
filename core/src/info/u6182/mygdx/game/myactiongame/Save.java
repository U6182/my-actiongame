/**
 * @file Save.java
 * @brief セーブデータの管理を行うクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import info.u6182.api.Utiility;

/**
 * @class Save
 * @brief セーブデータの管理を行うクラス
 */
public class Save {

    //! 自身のインスタンス
    private static final Save SAVE = new Save();

    /**
     * @brief コンストラクタ
     */
    private Save(){
        ;
    }

    /**
     * @brief 暗号鍵の保存を行う
     * @param key 暗号鍵
     * @param iv 初期化ベクトル
     */
    public void keySave(SecretKey key, IvParameterSpec iv){

        String localPath = Gdx.files.getLocalStoragePath();

        //鍵の直列化の保存
        try(ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(localPath + "Save/KeySave.dat"))){

            oss.writeObject(key);

            oss.flush();

        }catch (IOException e){

            e.printStackTrace();

        }

        //鍵の初期化ベクトルの保存
        FileHandle fileHandle = Gdx.files.local("Save/IvSave.dat");

        fileHandle.writeBytes(iv.getIV(), false);

    }

    /**
     * @brief 設定の保存を行う
     * @param key 暗号鍵
     * @param iv 初期化ベクトル
     * @param settings 設定オブジェクト
     */
    public void settingsSave(SecretKey key, IvParameterSpec iv, Settings settings){

        byte[] binary = Utiility.objectBinary(settings);

        //暗号化
        byte[] encryption = Utiility.encryption(key, iv, binary);

        FileHandle fileHandle = Gdx.files.local("Save/SettingSave.dat");

        //暗号化設定を保存
        fileHandle.writeBytes(encryption, false);

    }

    /**
     * @brief 過去最高のクリアステージ番号の保存を行う
     * @param key 暗号鍵
     * @param iv 初期化ベクトル
     * @param highClearStageNo 過去最高のクリアステージ番号
     */
    public void highClearStageNo(SecretKey key, IvParameterSpec iv, int highClearStageNo){

        //暗号化
        byte[] encryption = Utiility.encryptionInt(key, iv, highClearStageNo);

        FileHandle fileHandle = Gdx.files.local("Save/HighClearStageNoSave.dat");

        //暗号化名声スコアの保存
        fileHandle.writeBytes(encryption, false);

    }

    /**
     * @brief 過去最高の名声スコアの保存を行う
     * @param key 暗号鍵
     * @param iv 初期化ベクトル
     * @param highFameScore 過去最高の名声スコア
     */
    public void highFameScoreSave(SecretKey key, IvParameterSpec iv, int highFameScore){

        //暗号化
        byte[] encryption = Utiility.encryptionInt(key, iv, highFameScore);

        FileHandle fileHandle = Gdx.files.local("Save/HighFameScoreSave.dat");

        //暗号化名声スコアの保存
        fileHandle.writeBytes(encryption, false);

    }

    /**
     * @brief ステージ名声スコアの保存を行う
     * @param stageNo ステージ番号
     * @param fameScore 名声スコア
     */
    public void stageFameScoreSave(int stageNo, int fameScore){

        GameElement.getGameElement().setStageFameScore(stageNo, fameScore);

    }

    /**
     * @brief Saveのインスタンスの取得を行う
     * @return Saveのインスタンス
     */
    public static Save getSave(){

        return SAVE;

    }

}
