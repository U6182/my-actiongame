/**
 * @file CreditAnimation.java
 * @brief クレジットアニメーションの管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.credit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.GameElement;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.Utiility;
import info.u6182.api.fontmanager.FontManager;

/**
 * @class CreditAnimation
 * @brief クレジットアニメーションのクラス
 */
public class CreditAnimation {

    //! アニメーションオフセット
    private final float CREDITANIMATION_OFFSET = 50f;

    //! クレジットのスクロール値
    private final float CREDITANIMATION_CREDITSCROLL = 1.5f;

    //! アニメーションインターバルタイム
    private final float CREDITANIMATION_INTERVALTIME = 5f;

    //! クレジットステージ
    private CreditAnimationStage stage;

    //! クレジットプレイヤー
    private CreditAnimationPlayer player;

    //! クレジットエネミー
    private ArrayList<CreditAnimationEnemy> enemyList = new ArrayList<>();

    //! クレジットのリスト
    private ArrayList<String> creditList = new ArrayList<>();

    //! クレジット座標
    private Vector2[] creditPos;

    //! クレジットカラー
    private Color[] creditColor;

    //! キャスト一覧
    private final String[] CAST_STRING = {"ぴぽや倉庫:ブラックゴースト:ホワイトゴースト", "ぴぽや倉庫:パンプキン", "ぴぽや倉庫:キングゴースト", "ぴぽや倉庫:グリーンパンプキン", "ぴぽや倉庫:ゴーストパンプキン", "ぴぽや倉庫:パンプキング"};

    //! アナザーストーリーフラグ
    private boolean bAnotehrStory;

    //! クリア分岐フラグ
    private boolean bBranchClear;

    //! クレジット終了フラグ
    private boolean bEnd;

    //! クレジット中フラグ
    private boolean bCredit;

    //! インターバルフラグ
    private boolean bCreditInterval;

    //! フェードアウトフラグ
    private boolean bFaedOut;

    //! 透過フラグ
    private boolean bAlpha;

    //! 移動終了フラグ
    private boolean bMoveEnd;

    //! ステージ番号
    private int stageNo;

    //! アルファ値
    private float alpha;

    //! キャスト座標X
    private float[] castPosX;

    //! キャスト座標Y
    private float castPosY;

    //! クレジットインターバル
    private float creditInterval;

    /**
     * @brief コンストラクタ
     * @param bAnotehrStory アナザーストーリーフラグ
     * @param bBranchClear 分岐クリアフラグ
     */
    public CreditAnimation(boolean bAnotehrStory, boolean bBranchClear){

        this.bAnotehrStory = bAnotehrStory;

        this.bBranchClear = bBranchClear;

        this.bEnd = false;

        this.bCredit = this.bAnotehrStory ? true : false;

        this.bCreditInterval = false;

        this.bFaedOut = true;

        this.bAlpha = false;

        this.bMoveEnd = false;

        this.stageNo = 0;

        this.alpha = 0f;

        this.creditInterval = CREDITANIMATION_INTERVALTIME;

    }

    /**
     * @brief クレジット情報を読み込む
     */
    public void creditLoad(){

        String path = this.bAnotehrStory ? "Credit/AnotherCredit.txt" : "Credit/Credit.txt";

        //タイトル情報の読み込み
        FileHandle fileHandle = Gdx.files.internal(path);

        //文字列情報に格納
        String info = fileHandle.readString();

        //テンプ用
        String[] tempInfoSplit = info.split("[,\n\r]");

        List<String> infoSplit = new ArrayList<>();

        //タイトル情報の格納
        for(String str : tempInfoSplit){

            if(str.length() == 0){

                continue;

            }

            infoSplit.add(str);

        }

        String str;

        str = infoSplit.remove(0);

        //クレジットの数
        int creditCount = Integer.parseInt(str);

        this.creditPos = new Vector2[creditCount];

        this.creditColor = new Color[creditCount];

        for(int i = 0;i < creditCount;i++){

            this.creditPos[i] = new Vector2();

            this.creditColor[i] = new Color();

        }

        int fontSize = FontManager.getFontmanager().getSize();

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        //クレジットの読み込みと座標の初期化
        for(int i = 0;i < creditCount;i++){

            str = infoSplit.remove(0);

            int len = str.length();

            this.creditPos[i].x = Utiility.getScreenCenterX(len * fontSize);

            float posY = -(i * CREDITANIMATION_OFFSET);

            if(this.bAnotehrStory && i == creditCount - 1){

                this.creditPos[i].x += 23f;

                posY -= viewSize.y;

            }

            this.creditPos[i].y = posY;

            this.creditList.add(str);

            str = infoSplit.remove(0);

            int creditColor = Integer.parseInt(str);

            switch (creditColor){

                case 0:

                    this.creditColor[i] = Color.WHITE;

                    break;

                case 1:

                    this.creditColor[i] = Color.GREEN;

                    break;

                case 2:

                    this.creditColor[i] = Color.RED;

                    break;

                case 3:

                    this.creditColor[i] = new Color(0x80ff80ff);

                    break;

            }

        }

        if(!this.bAnotehrStory){

            this.castPosX = new float[this.CAST_STRING.length];

            for(int i = 0;i < this.CAST_STRING.length;i++){

                int len = this.CAST_STRING[i].length();

                this.castPosX[i] = Utiility.getScreenCenterX(len * fontSize);

            }

            this.castPosY = viewSize.y * 0.5f;

        }

    }

    /**
     * @brief クレジットアニメーションを読み込む
     * @param paht クレジットのパス
     */
    public void load(String paht){

        if(this.player == null){

            this.player = new CreditAnimationPlayer();

            this.player.load(this.bAnotehrStory, this.bBranchClear);

        }

        if(this.stage == null){

            this.stage = new CreditAnimationStage();

        }

        this.stage.load(paht);

    }

    /**
     * @brief クレジットアニメーションを初期化する
     * @param spriteBatch スプライトバッチ
     * @param stageNo ステージ番号
     */
    public void initialize(CustomSpriteBatch spriteBatch, int stageNo){

        if(this.bAnotehrStory){

            this.player.anotherInitialize(spriteBatch);

        }else{

            this.player.initialize(spriteBatch, stageNo);

        }

        //ステージと敵の初期化
        this.stage.initialize(this.enemyList, spriteBatch, this.bAnotehrStory, this.bBranchClear, stageNo);

    }

    /**
     * @brief アニメーションの実行を行う
     * @param spriteBatch スプライトバッチ
     */
    public void animation(CustomSpriteBatch spriteBatch){

        //ステージのシーンエフェクト処理が終了した場合
        if(!(this.bBranchClear || this.bAnotehrStory) && this.bFaedOut && this.stageNo <= GameElement.STAGE6){

            this.enemyList.clear();

            switch (this.stageNo){

                case GameElement.STAGE1:

                    load("Credit/CreditStage1.txt");

                    break;

                case GameElement.STAGE2:

                    load("Credit/CreditStage2.txt");

                    break;

                case GameElement.STAGE3:

                    load("Credit/CreditStage3.txt");

                    break;

                case GameElement.STAGE4:

                    load("Credit/CreditStage4.txt");

                    break;

                case GameElement.STAGE5:

                    load("Credit/CreditStage5.txt");

                    break;

                case GameElement.STAGE6:

                    load("Credit/CreditStage6.txt");

                    break;

            }

            initialize(spriteBatch, this.stageNo);

            this.stageNo++;

            this.bAlpha = false;

            this.bFaedOut = false;

        }

        update();

    }

    /**
     * @brief フェード処理を行う
     */
    public void faed(){

        //フェードアウト
        if(this.bAlpha){

            this.alpha -= 0.05f;

            if(this.alpha <= 0){

                //フェードアウト終了と共にテクスチャの描画を変える
                this.bFaedOut = true;

            }

        //フェードイン
        }else{

            this.alpha += 0.05f;

            if(this.alpha >= 1){

                this.alpha = 1f;

            }

        }

    }

    /**
     * @brief クレジットアニメーションの更新を行う
     */
    public void update(){

        //フェード処理の更新
        faed();

        //クレジットのスクロール処理の更新
        if(this.bCredit && !this.bCreditInterval){

            creditScroll();

        }

        //クレジット終了後のインターバル処理の更新
        if(this.bCreditInterval){

            decreaseInterval();

        }

        //ステージの衝突処理の更新
        this.stage.collision(this.player);

        //プレイヤーの更新
        this.player.update(this.bCreditInterval);

        //敵の更新
        if(!this.bAnotehrStory){

            enemyUpdate();

        }

    }

    /**
     * @brief クレジットのスクロール処理を行う
     */
    public void creditScroll(){

        //最終座標までクレジットのスクロールを行う
        for(int i = 0;i < this.creditList.size();i++){

            this.creditPos[i].y += this.CREDITANIMATION_CREDITSCROLL;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.bAnotehrStory){

            int fontSize = FontManager.getFontmanager().getSize();

            float endPosY = viewSize.y * 0.5f;

            if(this.creditPos[this.creditList.size() - 1].y + fontSize * 0.5f >= endPosY){

                this.bCreditInterval = true;

            }

        }else{

            if(this.creditPos[this.creditList.size() - 1].y > viewSize.y){

                this.bEnd = true;

            }

        }

    }

    /**
     * @brief インターバルタイムの減少処理を行う
     */
    public void decreaseInterval(){

        //クレジット終了後にインターバルタイムが0になった場合アニメーション終了
        this.creditInterval -= Gdx.graphics.getDeltaTime();

        if(this.creditInterval <= 0){

            this.bEnd = true;

        }

    }

    /**
     * @brief 敵の更新処理を行う
     */
    public void enemyUpdate(){

        //敵の更新を行う
        for(CreditAnimationEnemy enemy : this.enemyList){

            enemy.update();

            this.stage.collision(enemy);

            if(enemy.isMoveEnd() && this.stageNo > GameElement.STAGE6){

                this.bCredit = true;

            }else if(enemy.isMoveEnd()){

                this.bAlpha = true;

            }

        }

    }

    /**
     * @brief レンダリング処理を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        //ステージの描画
        this.stage.render(spriteBatch, this.alpha);

        //プレイヤーの描画
        this.player.render();

        //敵の描画
        for(CreditAnimationEnemy enemy : this.enemyList){

            enemy.render();

        }

        if(this.bCredit){

            for(int i = 0;i < this.creditList.size();i++){

                FontManager.getFontmanager().render(this.creditList.get(i), this.creditColor[i], this.creditPos[i].x, this.creditPos[i].y);

            }

        }else{

            FontManager.getFontmanager().render(this.CAST_STRING[this.stageNo - 1], new Color(1,1,1, this.alpha), this.castPosX[this.stageNo - 1], this.castPosY);

        }

    }

    /**
     * @brief アニメーションが終了しているかの確認を行う
     * @return アニメーションが終了している場合はtrue
     */
    public boolean isEnd(){

        return this.bEnd;

    }

}
