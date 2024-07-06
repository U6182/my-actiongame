/**
 * @file AnimeMotion
 * @brief アニメーション管理
 * @author 木村 憂哉
 * @date 2020/04/06
 */

package info.u6182.api.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class AnimeMotion
 * @brief アニメーション管理を行うクラス
 */
public class AnimeMotion {

    //! アニメーション情報
    private AnimeData[] animeData;

    //! バッチャ
    private CustomSpriteBatch spriteBatch;

    //! アニメーション終了フラグ
    private boolean[] bAnimationEnd;

    //! アニメーション数
    private int animeCount;

    //! アニメーション番号
    private int animeNo;

    //! 状態時間
    private float startTime;

    /**
     * @brief コンストラクタ
     * @param animeData アニメーションデータ
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public AnimeMotion(AnimeData[] animeData){

        //アニメーション情報の初期化
        this.animeData = animeData;

        //アニメーションの数の初期化
        this.animeCount = this.animeData.length - 1;

        //アニメーション終了フラグの初期化
        this.bAnimationEnd = new boolean[this.animeData.length];

        for(boolean bEnd : bAnimationEnd){

            bEnd = false;

        }

        //アニメーション番号の初期化
        this.animeNo = 0;

        //状態時間の初期化
        this.startTime = 0.0f;

    }

    /**
     * @brief コンストラクタ
     * @param animeData アニメーションデータ
     * @param spriteBatch バッチャ
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public AnimeMotion(AnimeData[] animeData, CustomSpriteBatch spriteBatch){

        //アニメーション情報の初期化
        this.animeData = animeData;

        //スプライトバッチャの初期化
        this.spriteBatch = spriteBatch;

        //アニメーションの数の初期化
        this.animeCount = this.animeData.length - 1;

        //アニメーション終了フラグの初期化
        this.bAnimationEnd = new boolean[this.animeData.length];

        for(boolean bEnd : bAnimationEnd){

            bEnd = false;

        }

        //アニメーション番号の初期化
        this.animeNo = 0;

        //状態時間の初期化
        this.startTime = 0.0f;

    }

    /**
     * @brief addTime
     * @param deltaTime FPSタイム
     * @return なし
     * @details 状態時間の加算を行う
     */
    public void addTime(float deltaTime){

        this.startTime += this.animeData[this.animeNo].isGameFrame() ? 1 : deltaTime;

        if(this.animeData[this.animeNo].getAnimation().getPlayMode() == Animation.PlayMode.NORMAL){

            //全てのフレーム時間に達した場合、状態時間を0で再初期化
            if(this.startTime > this.animeData[this.animeNo].getFrameCount() * this.animeData[animeNo].getFrameTime()){

                this.bAnimationEnd[this.animeNo] = true;

                this.startTime = this.animeData[this.animeNo].getFrameCount() * this.animeData[this.animeNo].getFrameTime() + 1f;

            }

            return;

        }

        //全てのフレーム時間に達した場合、状態時間を0で再初期化
        if(this.startTime > this.animeData[this.animeNo].getFrameCount() * this.animeData[this.animeNo].getFrameTime()){

            this.startTime = 0.0f;

        }

    }

    /**
     * @brief changeAnime
     * @param animeNo アニメーション番号
     * @return なし
     * @details アニメーションの変更を行う
     */
    public void changeAnime(int animeNo){

        if(animeNo > this.animeCount || this.animeNo == animeNo){

            return;

        }

        this.bAnimationEnd[animeNo] = false;

        this.animeNo = animeNo;

        this.startTime = 0.0f;

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y){

        if(this.spriteBatch == null){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        this.spriteBatch.draw(keyFrame, x, y);

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @param width サイズX
     * @param height サイズY
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y, float width, float height){

        if(this.spriteBatch == null){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        this.spriteBatch.draw(keyFrame, x, y, width, height);

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @param rgba 色情報
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y, float[] rgba){

        if(this.spriteBatch == null || rgba.length != 4){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        this.spriteBatch.setColor(rgba[0], rgba[1], rgba[2], rgba[3]);

        this.spriteBatch.draw(keyFrame, x, y);

        this.spriteBatch.setColor(1,1,1,1);

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @param width サイズX
     * @param height サイズY
     * @param rgba 色情報
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y, float width, float height, float[] rgba){

        if(this.spriteBatch == null || rgba.length != 4){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        this.spriteBatch.setColor(rgba[0], rgba[1], rgba[2], rgba[3]);

        this.spriteBatch.draw(keyFrame, x, y, width, height);

        this.spriteBatch.setColor(1,1,1,1);

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @param bWidthReverse 横反転フラグ
     * @param bHeightReverse 縦反転フラグ
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y, boolean bWidthReverse, boolean bHeightReverse){

        if(this.spriteBatch == null){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        Vector2 size = this.animeData[this.animeNo].getSize();

        float width = bWidthReverse ? -size.x : size.x;

        float offsetX = bWidthReverse ? -width : 0;

        float height = bHeightReverse ? -size.y : size.y;

        float offsetY = bHeightReverse ? -height : 0;

        this.spriteBatch.draw(keyFrame, x + offsetX, y + offsetY, width, height);

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @param bWidthReverse 横反転フラグ
     * @param bHeightReverse 縦反転フラグ
     * @param alpha 色情報
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y, boolean bWidthReverse, boolean bHeightReverse, float alpha){

        if(this.spriteBatch == null){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        this.spriteBatch.setColor(1f, 1f, 1f, alpha);

        Vector2 size = this.animeData[this.animeNo].getSize();

        float width = bWidthReverse ? -size.x : size.x;

        float offsetX = bWidthReverse ? -width : 0;

        float height = bHeightReverse ? -size.y : size.y;

        float offsetY = bHeightReverse ? -height : 0;

        this.spriteBatch.draw(keyFrame, x + offsetX, y + offsetY, width, height);

        this.spriteBatch.setColor(1,1,1,1);

    }

    /**
     * @brief render
     * @param x 始点X
     * @param y 始点Y
     * @param bWidthReverse 横反転フラグ
     * @param bHeightReverse 縦反転フラグ
     * @param rgba 色情報
     * @return なし
     * @details アニメーションの描画を行う
     */
    public void render(float x, float y, boolean bWidthReverse, boolean bHeightReverse, float[] rgba){

        if(this.spriteBatch == null || rgba.length != 4){

            return;

        }

        TextureRegion keyFrame = getKeyFrame();

        this.spriteBatch.setColor(rgba[0], rgba[1], rgba[2], rgba[3]);

        Vector2 size = this.animeData[this.animeNo].getSize();

        float width = bWidthReverse ? -size.x : size.x;

        float offsetX = bWidthReverse ? -width : 0;

        float height = bHeightReverse ? -size.y : size.y;

        float offsetY = bHeightReverse ? -height : 0;

        this.spriteBatch.draw(keyFrame, x + offsetX, y + offsetY, width, height);

        this.spriteBatch.setColor(1,1,1,1);

    }

    /**
     * @brief resetStartTime
     * @details 状態時間のリセットを行う
     */
    public void resetStartTime(){

        this.bAnimationEnd[this.animeNo] = false;

        this.startTime = 0f;

    }

    /**
     * @brief setSpriteBatch
     * @param spriteBatch バッチャ
     * @return なし
     * @details バッチャの設定を行う
     */
    public void setSpriteBatch(CustomSpriteBatch spriteBatch){

        if(this.spriteBatch != null && this.spriteBatch.equals(spriteBatch)){

            return;

        }

        this.spriteBatch = spriteBatch;

    }

    /**
     * @brief getKeyFrame
     * @return なし
     * @details キーフレームの取得を行う
     */
    public TextureRegion getKeyFrame(){

        return (TextureRegion) this.animeData[animeNo].getAnimation().getKeyFrame(this.startTime);

    }

    /**
     * @brief isAnimationEnd
     * @return boolean アニメーション終了フラグ
     * @details アニメーション終了フラグの取得を行う
     */
    public boolean isAnimationEnd(){

        return this.bAnimationEnd[this.animeNo];

    }

    /**
     * @brief getAnimeNo
     * @return int アニメーション番号
     * @details アニメーション番号の取得を行う
     */
    public int getAnimeNo(){

        return this.animeNo;

    }

}
