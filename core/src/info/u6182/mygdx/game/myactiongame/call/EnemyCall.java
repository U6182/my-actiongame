/**
 * @file EnemyCall.java
 * @brief 敵キャラクターの召喚を管理するクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.call;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.CameraInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.BlackGhost;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.GhostPumpkin;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.GreenPumpkin;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.KingGhost;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.Pumpkin;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.WhiteGhost;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class EnemyCall
 * @brief 敵キャラクターの召喚を管理するクラス
 */
public class EnemyCall extends Call {

    //! スプライトバッチ
    private CustomSpriteBatch spriteBatch;

    //! 敵キャラクターのリスト
    private List<Enemy> enemyList = new ArrayList<>();

    //! カメラ情報
    private CameraInfo cameraInfo;

    //! 変更フラグ
    private boolean bChange;

    //! タイムリミットフラグ
    private boolean bTimeLimit;

    //! 召喚数
    private int callCount;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public EnemyCall(CustomSpriteBatch spriteBatch){

        this.spriteBatch = spriteBatch;

        this.cameraInfo = new CameraInfo();

        this.bChange = false;

        this.bTimeLimit = false;

        this.callCount = 0;

    }

    /**
     * @brief 敵キャラクターの更新処理を行う
     * @return 召喚コスト
     */
    public int update(){

        int callCost = 0;

        for(int i = 0;i < this.callCount;i++){

            if(!this.bTimeLimit){

                //敵の更新
                this.enemyList.get(i).update(Gdx.graphics.getDeltaTime());

            }

            if(this.enemyList.get(i).getType() == GameElement.ENEMY_KINGGHOST){

                //召喚時間の処理
                timeLimit(i);

            }

            if(!this.enemyList.get(i).isShow()){

                callCost = this.enemyList.get(i).getCallCost();

                release(i);

            }

        }

        return callCost;

    }

    /**
     * @brief 敵キャラクターの描画処理を行う
     */
    public void render(){

        //敵の描画
        for(Enemy enemy : this.enemyList){

            enemy.render();

        }

    }

    /**
     * @brief 解放処理を行う
     * @param i インデックス
     */
    public void release(int i){

        this.enemyList.remove(i);

        if(this.bChange){

            this.bChange = false;

            if(SoundManager.getSoundManager().isSubBGMPlaying(SoundInfo.BGM_PLAYERCHANGE)){

                SoundManager.getSoundManager().subBGMStop();

            }

            SoundManager.getSoundManager().allResume();

            this.cameraInfo.setReset(true);

        }

        this.callCount--;

    }

    /**
     * @brief 敵キャラクターを召喚する
     * @param pos 出現座標
     * @param size キャラクターのサイズ
     * @param bRevese 反転フラグ
     * @param type キャラクターのタイプ
     */
    public void call(Vector2 pos, Vector2 size, boolean bRevese, int type){

        Texture texture = null;

        //敵の生成
        switch (type){

            case GameElement.ENEMY_PUMPKIN:

                texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKIN);

                this.enemyList.add(new Pumpkin(this.spriteBatch, texture, pos, size, bRevese, type));

                break;

            case GameElement.ENEMY_GREENPUMPKIN:

                texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GREENPUMPKIN);

                this.enemyList.add(new GreenPumpkin(this.spriteBatch, texture, pos, size, bRevese, type));

                break;

            case GameElement.ENEMY_GHOSTPUMPKIN:

                texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GHOSTPUMPKIN);

                this.enemyList.add(new GhostPumpkin(this.spriteBatch, texture, pos, size, bRevese, type));

                break;

            case GameElement.ENEMY_WHITEGHOST:

                texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_WHITEGHOST);

                this.enemyList.add(new WhiteGhost(this.spriteBatch, texture, pos, size, bRevese, type));

                break;

            case GameElement.ENEMY_BLACKGHOST:

                texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BLACKGHOST);

                this.enemyList.add(new BlackGhost(this.spriteBatch, texture, pos, size, bRevese, type));

                break;

            case GameElement.ENEMY_KINGGHOST:

                texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KINGGHOST);

                this.enemyList.add(new KingGhost(this.spriteBatch, texture, pos, size, bRevese, type));

                Vector2 cameraPos = GameElement.getGameElement().getCameraPos();

                Vector2 scroll = GameElement.getGameElement().getScroll();

                this.cameraInfo.change(cameraPos, scroll);

                break;

        }

        this.callCount++;

    }

    /**
     * @brief タイムリミットの処理を行う
     * @param i インデックス
     */
    public void timeLimit(int i){

        if(!this.bChange){

            this.bChange = true;

            SoundManager.getSoundManager().allPause();

            SoundManager.getSoundManager().subBGMPlay(SoundInfo.BGM_PLAYERCHANGE, false);

        }

        if(!this.bChange){

            return;

        }
        if(!SoundManager.getSoundManager().isSubBGMPlaying(SoundInfo.BGM_PLAYERCHANGE)){

            if(!this.bTimeLimit && !this.enemyList.get(i).isDead()){

                Vector2 size = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_CALL);

                Vector2 pos = new Vector2(this.enemyList.get(i).getPos().x - size.x * 0.5f, this.enemyList.get(i).getPos().y - size.y * 0.5f);

                EffectManager.getEffectManager().start(EffectInfo.EFFECT_CALL, pos);

                this.bTimeLimit = true;

            }

            if(!EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_CALL) && this.bTimeLimit){

                this.enemyList.get(i).setShow(false);

                this.bTimeLimit = false;

            }

        }

    }

    /**
     * @brief 敵キャラクターとステージの衝突処理を行う
     * @param buried 埋め込み量
     * @param i インデックス
     */
    public void collisionStage(Vector2 buried, int i){

        this.enemyList.get(i).callCollisionStage(buried);

    }

    /**
     * @brief 変更フラグの設定を行う
     * @param bChange 変更フラグ
     */
    public void setChange(boolean bChange){

        this.bChange = bChange;

    }

    /**
     * @brief 敵キャラクターのリストを取得する
     * @return 敵キャラクターのリスト
     */
    public List<Enemy> getEnemyList(){

        return this.enemyList;

    }

    /**
     * @brief 敵キャラクターの取得を行う
     * @param i インデックス
     * @return 敵キャラクター
     */
    public Enemy getEnemy(int i){

        return this.enemyList.get(i);

    }

    /**
     * @brief カメラ情報の取得を行う
     * @return カメラ情報
     */
    public CameraInfo getCameraInfo(){

        return this.cameraInfo;

    }

    /**
     * @brief 変更フラグの取得を行う
     * @return 変更フラグ
     */
    public boolean isChange(){

        return this.bChange;

    }

    /**
     * @brief 召喚数の取得を行う
     * @return 召喚数
     */
    public int getCallCount(){

        return this.callCount;

    }

}
