/**
 * @file FireBall.java
 * @brief ゲーム内のファイアボールクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.fireball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.Substance;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class FireBall
 * @brief ゲーム内のファイアボールクラス
 */
public class FireBall extends Substance {

    //! 跳ねフラグ
    private boolean bBounce;

    //! ファイヤーボールの跳力
    private final float FIREBALL_BOUNCE = 5f;

    /**
     * @brief コンストラクタ
     */
    public FireBall(){

        //座標の初期化
        this.pos = new Vector2();

        //移動量の初期化
        this.move = new Vector2();

        //サイズの初期化
        this.size = new Vector2(16f, 16f);

        //矩形の初期化
        this.rect = new CustomRectangle();

        //跳ねフラグの初期化
        this.bBounce = false;

    }

    /**
     * @brief 更新処理を行う
     */
    public void update(){

        if(!this.bShow){

            return;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        //上以外の画面外に出た場合非表示にする
        if(pos.y + this.size.y < 0 || pos.x > viewSize.x || pos.x + this.size.x < 0){

            this.bShow = false;

            return;

        }

        //動作の更新
        behavior();

    }

    /**
     * @brief ファイアボールの動作処理を行う
     */
    public void behavior(){

        //常時跳ねさせる
        if(!this.bBounce){

            this.bBounce = true;

            this.move.y = FIREBALL_BOUNCE;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief 敵との衝突判定を行う
     * @param enemy 敵のインスタンス
     * @return 衝突している場合はtrueを返す
     */
    public boolean collisionEnemy(Enemy enemy){

        CustomRectangle frec = getRect();

        CustomRectangle erec = enemy.getRect();

        //ファイアボールが敵と衝突した場合
        if(frec.overlaps(erec)){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_ATTACK);

            this.bShow = false;

            return true;

        }

        return false;

    }

    /**
     * @brief ステージとの衝突処理を行う
     * @param buried 埋まり値
     */
    public void collisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //下辺の埋まり
        if(buried.y > 0 && this.move.y < 0){

            if(this.bBounce){

                this.bBounce = false;

            }

            this.move.y = 0;

        //上辺の埋まり
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0;

        }

        //右辺の埋まり
        if(buried.x < 0 && this.move.x > 0){

            this.bShow = false;

        //左辺の埋まり
        }else if(buried.x > 0 && this.move.x < 0){

            this.bShow = false;

        }

    }

    /**
     * @brief 描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        if(!this.bShow){

            return;

        }

        Texture fireBall = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_FIREBALL);

        //ファイアボールの描画
        spriteBatch.draw(fireBall, this.pos.x, this.pos.y);

    }

    /**
     * @brief バウンスフラグの設定を行う
     * @param bBounce 跳ねフラグ
     */
    public void setBounce(boolean bBounce){

        this.bBounce = bBounce;

    }

}
