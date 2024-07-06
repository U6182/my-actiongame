/**
 * @file AnimeData
 * @brief アニメーション情報
 * @author 木村 憂哉
 * @date 2020/04/06
 */

package info.u6182.api.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @class AnimeData
 * @brief アニメーション情報を管理するクラス
 */
public class AnimeData {

    //! アニメーション
    private Animation animation;

    //! アニメーションの名前
    private String animeName;

    //! キーフレームのサイズ
    private Vector2 size;

    //! ゲームフレームフラグ
    private boolean bGameFrame;

    //! フレーム数
    private int frameCount;

    //! フレーム実行時間
    private float frameTime;

    /**
     * @brief コンストラクタ
     * @param texture アニメーションテクスチャ
     * @param animeName アニメーションの名前
     * @param size キーフレームのサイズ
     * @param bLoop ループフラグ
     * @param animeOffSet アニメーションキーフレームの基準値
     * @param offSetX アニメーションの読み込み開始X
     * @param offSetY アニメーションの読み込み開始Y
     * @param frameTime フレーム実行時間
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public AnimeData(Texture texture, String animeName, Vector2 size, boolean bLoop, int[][] animeOffSet, int offSetX, int offSetY, float frameTime){

        //アニメーションの名前の設定
        this.animeName = animeName;

        //アニメーションのサイズの設定
        this.size = size;

        //ゲームフレームフラグの設定
        this.bGameFrame = false;

        //フレーム実行時間の設定
        this.frameTime = frameTime;

        //各フレーム
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //フレーム数
        this.frameCount = animeOffSet.length;

        for(int y = 0;y < this.frameCount;y++){

            if(animeOffSet[y].length > 2){

                return;

            }

            //[0][0]がX [0][1]がY
            TextureRegion region = new TextureRegion(texture, (int)(offSetX + (size.x * animeOffSet[y][0])), (int)(offSetY + (size.y * animeOffSet[y][1])), (int)size.x, (int)size.y);

            frames.add(region);

        }

        //ループ設定
        Animation.PlayMode playMode = bLoop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL;

        //アニメーションの生成
        this.animation = new Animation(this.frameTime, frames, playMode);

    }

    /**
     * @brief コンストラクタ
     * @param texture アニメーションテクスチャ
     * @param animeName アニメーションの名前
     * @param size キーフレームのサイズ
     * @param bLoop ループフラグ
     * @param animeOffSet アニメーションキーフレームの基準値
     * @param offSetX アニメーションの読み込み開始X
     * @param offSetY アニメーションの読み込み開始Y
     * @param gameFrameCount フレーム実行フレーム数
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public AnimeData(Texture texture, String animeName, Vector2 size, boolean bLoop, int[][] animeOffSet, int offSetX, int offSetY, int gameFrameCount){

        //アニメーションの名前の設定
        this.animeName = animeName;

        //アニメーションのサイズの設定
        this.size = size;

        //ゲームフレームフラグの設定
        this.bGameFrame = true;

        //フレーム実行時間の設定
        this.frameTime = gameFrameCount;

        //各フレーム
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //フレーム数
        this.frameCount = animeOffSet.length;

        for(int y = 0;y < this.frameCount;y++){

            if(animeOffSet[y].length > 2){

                return;

            }

            //[0][0]がX [0][1]がY
            TextureRegion region = new TextureRegion(texture, (int)(offSetX + (size.x * animeOffSet[y][0])), (int)(offSetY + (size.y * animeOffSet[y][1])), (int)size.x, (int)size.y);

            frames.add(region);

        }

        //ループ設定
        Animation.PlayMode playMode = bLoop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL;

        //アニメーションの生成
        this.animation = new Animation(this.frameTime, frames, playMode);

    }

    /**
     * @brief getAnimation
     * @return Animation 生成したアニメーション
     * @details 生成したアニメーションの取得を行う
     */
    public Animation getAnimation(){

        return this.animation;

    }

    /**
     * @brief getAnimeName
     * @return String アニメーションの名前
     * @details アニメーションの名前の取得を行う
     */
    public String getAnimeName(){

        return this.animeName;

    }

    /**
     * @brief getSize
     * @return Vector2 キーフレームのサイズ
     * @details キーフレームのサイズの取得を行う
     */
    public Vector2 getSize(){

        return this.size;

    }

    /**
     * @brief isGameFrame
     * @return boolean ゲームフレームフラグの状態
     * @details ゲームフレームフラグの状態を取得を行う
     */
    public boolean isGameFrame(){

        return this.bGameFrame;

    }

    /**
     * @brief getFrameCount
     * @return int フレーム数を取得
     * @details フレーム数の取得を行う
     */
    public int getFrameCount(){

        return this.frameCount;

    }

    /**
     * @brief getFrameTime
     * @return float フレーム実行時間
     * @details フレーム実行時間の取得を行う
     */
    public float getFrameTime(){

        return this.frameTime;

    }

}