/**
 * @file FireBallCall.java
 * @brief ファイアボールの召喚を管理するクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.call;

import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.substance.fireball.FireBall;

import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.soundmanager.SoundManager;

/**
 * @class FireBallCall
 * @brief ファイアボールの召喚を管理するクラス
 */
public class FireBallCall extends Call {

    //! ファイアボール
    private FireBall fireBall;

    //! ファイアボールのスピード
    private final float FIREBALL_SPEED = 8f;

    /**
     * @brief コンストラクタ
     */
    public FireBallCall(){

        this.fireBall = new FireBall();

    }

    /**
     * @brief ファイアボールの更新処理を行う
     */
    public void update(){

        //ファイアボールの更新
        this.fireBall.update();

    }

    /**
     * @brief ファイアボールを発射する
     * @param playerPos プレイヤーの位置
     * @param playerSize プレイヤーのサイズ
     * @param reverse 反転フラグ
     */
    public void fire(Vector2 playerPos, Vector2 playerSize, boolean reverse){

        if(this.fireBall.isShow()){

            return;

        }

        float posX;
        float moveX;

        //プレイヤーの向きに応じて発射
        if(reverse){

            posX = playerPos.x + this.fireBall.getSize().x * 0.5f;

            moveX = -FIREBALL_SPEED;

        }else {

            posX = playerPos.x + playerSize.x - this.fireBall.getSize().x * 1.5f;

            moveX = FIREBALL_SPEED;

        }

        this.fireBall.getPos().set(posX, playerPos.y + playerSize.y - 15.0f);

        this.fireBall.getMove().set(moveX, 0f);

        this.fireBall.setBounce(false);

        this.fireBall.setShow(true);

        SoundManager.getSoundManager().play(SoundInfo.SOUND_FIRESHOT);

    }

    /**
     * @brief ファイアボールの描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        //ファイアボールの描画
        this.fireBall.render(spriteBatch);

    }

    /**
     * @brief ファイアボールの取得を行う
     * @return ファイアボール
     */
    public FireBall getFireBall(){

        return this.fireBall;

    }

}
