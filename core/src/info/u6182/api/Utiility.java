/**
 * @file Utility.java
 * @brief ユーティリティクラスのファイル
 * @autor 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.Settings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @class Utility
 * @brief ユーティリティクラス
 */
public class Utiility {

    //! ベクトルのインスタンス
    private static Vector2 vector2 = new Vector2();

    //! ランダムのインスタンス
    private static Random random = new Random();

    //! 暗号化キー
    private static final String ENCRYPTION_KEY = "u6182_ENCRYPT";

    //! 初期化ベクトル
    private static final String IVC = "1234567890123456";

    /**
     * @brief objectBinary
     * @param object オブジェクト
     * @return byte[] バイナリデータ
     * @details オブジェクトをバイナリデータへ変換を行う
     */
    public static byte[] objectBinary(Settings object){

        String localPath = Gdx.files.getLocalStoragePath();

        //鍵の直列化の保存
        try(ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(localPath + "Save/Binary.dat"))){

            oss.writeObject(object);

            oss.flush();

        }catch (IOException e){

            e.printStackTrace();

        }

        FileHandle fileHandle = Gdx.files.local("Save/Binary.dat");

        byte[] objectBinary = fileHandle.readBytes();

        fileHandle.delete();

        return objectBinary;

    }

    /**
     * @brief generateKey
     * @return SecretKey 秘密鍵
     * @details AESアルゴリズムで秘密鍵の生成を行う
     */
    public static SecretKey generateKey(){

        KeyGenerator keyGenerator = null;

        try {

            //指定したアルゴリズムの秘密鍵の生成
            keyGenerator = KeyGenerator.getInstance("AES");

        }catch (NoSuchAlgorithmException nse){

            nse.printStackTrace();

        }

        //指定した鍵サイズで初期化
        keyGenerator.init(128);

        //秘密鍵の生成
        return keyGenerator.generateKey();

    }

    /**
     * @brief generateIV
     * @return IvParameterSpec 初期化ベクトル
     * @details AESアルゴリズムで初期化ベクトルの生成を行う
     */
    public static IvParameterSpec generateIV(){

        SecureRandom random = new SecureRandom();

        byte[] iv = new byte[16];

        //指定したバイト数の乱数を生成
        random.nextBytes(iv);

        //初期化ベクトルの生成
        return new IvParameterSpec(iv);

    }

    /**
     * @brief encryption
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @param encryption 暗号化するデータ
     * @return byte[] 暗号化されたデータ
     * @details データの暗号化を行う
     */
    public static byte[] encryption(SecretKey key, IvParameterSpec iv, byte[] encryption){

        Cipher encrypter = null;

        byte[] encryptionData = null;

        try {

            encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");

            encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

            encryptionData = encrypter.doFinal(encryption);

        }catch (GeneralSecurityException gse){

            gse.printStackTrace();

        }

        return encryptionData;

    }

    /**
     * @brief decryption
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @param crypto 暗号化されたデータ
     * @return byte[] 復号化されたデータ
     * @details データの復号化を行う
     */
    public static byte[] decryption(SecretKey key, IvParameterSpec iv, byte[] crypto){

        Cipher decrypter = null;

        byte[] decryptionData = null;

        try {

            decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");

            decrypter.init(Cipher.DECRYPT_MODE, key, iv);

            decryptionData = decrypter.doFinal(crypto);

        }catch (GeneralSecurityException gse){

            gse.printStackTrace();

        }

        return decryptionData;

    }

    /**
     * @brief encryptionInt
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @param fameScore 暗号化する整数
     * @return byte[] 暗号化された整数データ
     * @details 整数データの暗号化を行う
     */
    public static byte[] encryptionInt(SecretKey key, IvParameterSpec iv, int fameScore){

        String encryptionStrFameScore = Integer.toString(fameScore);

        Cipher encrypter = null;

        byte[] encryptionFameScore = null;

        try {

            encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");

            encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

            encryptionFameScore = encrypter.doFinal(encryptionStrFameScore.getBytes());

        }catch (GeneralSecurityException gse){

            gse.printStackTrace();

        }

        return encryptionFameScore;

    }

    /**
     * @brief decryptionInt
     * @param key 秘密鍵
     * @param iv 初期化ベクトル
     * @param cryptoFameScore 暗号化された整数データ
     * @return int 復号化された整数
     * @details 整数データの復号化を行う
     */
    public static int decryptionInt(SecretKey key, IvParameterSpec iv, byte[] cryptoFameScore){

        Cipher decrypter = null;

        int fameScore = 0;

        try {

            decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");

            decrypter.init(Cipher.DECRYPT_MODE, key, iv);

            fameScore = Integer.parseInt(new String(decrypter.doFinal(cryptoFameScore)));

        }catch (GeneralSecurityException gse){

            gse.printStackTrace();

        }

        return fameScore;

    }

    /**
     * @brief getScrollSubPos
     * @param pos 位置ベクトル
     * @param scroll スクロールベクトル
     * @return Vector2 スクロール後の位置ベクトル
     * @details スクロール後の位置の取得を行う
     */
    public static Vector2 getScrollSubPos(Vector2 pos, Vector2 scroll){

        return vector2.set(pos.x - scroll.x, pos.y - scroll.y);

    }

    /**
     * @brief getScreenCenterX
     * @param sizeX 横幅
     * @return float 画面中央のX座標
     * @details 画面中央のX座標の取得を行う
     */
    public static float getScreenCenterX(float sizeX){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        return (viewSize.x - sizeX) * 0.5f;

    }

    /**
     * @brief getScreenCenterX
     * @param sizeX 横幅
     * @param viewWidth 画面の幅
     * @return float 画面中央のX座標
     * @details 画面幅を考慮して画面中央のX座標の取得を行う
     */
    public static float getScreenCenterX(float sizeX, float viewWidth){

        return (viewWidth - sizeX) * 0.5f;

    }

    /**
     * @brief getScreenCenterY
     * @param sizeY 縦幅
     * @return float 画面中央のY座標
     * @details 画面中央のY座標の取得を行う
     */
    public static float getScreenCenterY(float sizeY){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        return (viewSize.y - sizeY) * 0.5f;

    }

    /**
     * @brief getScreenCenterY
     * @param sizeY 縦幅
     * @param viewHeight 画面の高さ
     * @return float 画面中央のY座標
     * @details 画面高さを考慮して画面中央のY座標の取得を行う
     */
    public static float getScreenCenterY(float sizeY, float viewHeight){

        return (viewHeight - sizeY) * 0.5f;

    }

    /**
     * @brief getScreenCenter
     * @param size サイズベクトル
     * @return Vector2 画面中央の位置ベクトル
     * @details 画面中央の位置の取得を行う
     */
    public static Vector2 getScreenCenter(Vector2 size){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        return vector2.set((viewSize.x - size.x) * 0.5f, (viewSize.y - size.y) * 0.5f);

    }

    /**
     * @brief getScreenCenter
     * @param sizeX 横幅
     * @param sizeY 縦幅
     * @return Vector2 画面中央の位置ベクトル
     * @details 画面中央の位置の取得を行う
     */
    public static Vector2 getScreenCenter(float sizeX, float sizeY){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        return vector2.set((viewSize.x - sizeX) * 0.5f, (viewSize.y - sizeY) * 0.5f);

    }

    /**
     * @brief getObsessionCount
     * @param guilt ギルトカウント
     * @return int ギルトカウントのランダム値
     * @details ランダムなギルトカウントの取得を行う
     */
    public static int getObsessionCount(int guilt){

        return random.nextInt(guilt) + 1;

    }

    /**
     * @brief getRandomInt
     * @param maxRandomNum 最大ランダム値
     * @return int ランダムな整数値
     * @details 最大ランダム値までのランダムな整数値の取得を行う
     */
    public static int getRandomInt(int maxRandomNum){

        return random.nextInt(maxRandomNum);

    }

    /**
     * @brief getRandomInt
     * @param minRandomNum 最小ランダム値
     * @param maxRandomNum 最大ランダム値
     * @return int ランダムな整数値
     * @details 最小ランダム値から最大ランダム値までのランダムな整数値の取得を行う
     */
    public static int getRandomInt(int minRandomNum, int maxRandomNum){

        return random.nextInt(maxRandomNum - minRandomNum) + minRandomNum;

    }

}
