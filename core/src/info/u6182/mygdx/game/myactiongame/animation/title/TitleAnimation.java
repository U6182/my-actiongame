/**
 * @file TitleAnimation.java
 * @brief タイトルアニメーションの管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.title;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class TitleAnimation
 * @brief タイトル画面でのアニメーションを管理するクラス
 */
public class TitleAnimation {

    //! バッチャ
    private CustomSpriteBatch spriteBatch;

    //! ステージ
    private TitleAnimationStage stage;

    //! プレイヤー
    private TitleAnimationPlayer player;

    //! 敵
    private List<TitleAnimationEnemy> enemyList;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public TitleAnimation(CustomSpriteBatch spriteBatch){

        //バッチャの設定
        this.spriteBatch = spriteBatch;

        //プレイヤーの生成
        this.player = new TitleAnimationPlayer(spriteBatch);

        //ステージの生成
        this.stage = new TitleAnimationStage();

        //ステージの読み込み
        this.stage.load("Title/Title.txt");

        this.enemyList = new ArrayList<>();

        //プレイヤーの初期化
        this.player.initialize();

        //ステージの初期化
        this.stage.initialize(this.enemyList, spriteBatch);

    }

    /**
     * @brief アニメーションの初期化
     */
    public void initialize(){

        //敵のクリア
        this.enemyList.clear();

        //プレイヤーの初期化
        this.player.initialize();

        //ステージの初期化
        this.stage.initialize(this.enemyList, this.spriteBatch);

    }

    /**
     * @brief アニメーションの更新処理を行う
     * @param deltaTime 経過時間
     */
    public void animation(float deltaTime){

        //アニメーションが終了した場合
        if(this.player.isEnd()){

            initialize();

        }

        //プレイヤーの更新
        this.player.update(deltaTime);

        //敵の出現
        switch (this.player.getCount()){

            case 1:

                if(!this.enemyList.get(0).isShow()){

                    this.enemyList.get(0).getMove().set(-5, 0);
                    this.enemyList.get(0).setShow(true);

                }

                break;

            case 2:

                if(!this.enemyList.get(1).isShow() && this.enemyList.get(0).getMove().x > 0){

                    this.enemyList.get(1).getMove().set(5, 0);
                    this.enemyList.get(1).setShow(true);

                }

                break;

            case 3:

                if(!this.enemyList.get(2).isShow() && this.enemyList.get(1).getMove().x < 0){

                    this.enemyList.get(2).getMove().set(-5, 0);
                    this.enemyList.get(2).setShow(true);

                }

                break;

        }

        //更新
        update(deltaTime);

    }

    /**
     * @brief アニメーションの更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //敵の更新
        for(TitleAnimationEnemy enemy : this.enemyList){

            enemy.update(this.player.isEnemyStop(), deltaTime);

        }

        //敵とプレイヤーの衝突判定
        for(TitleAnimationEnemy enemy : this.enemyList){

            this.player.collisionEnemy(enemy);

        }

    }

    /**
     * @brief アニメーションの描画処理を行う
     * @param spriteBatch スプライトバッチ
     * @param deltaTime 経過時間
     */
    public void render(CustomSpriteBatch spriteBatch, float deltaTime){

        //ステージの描画
        this.stage.render(spriteBatch);

        //プレイヤーの描画
        this.player.render();

        //敵の描画
        for(TitleAnimationEnemy enemy : this.enemyList){

            enemy.render();

        }

    }

}
