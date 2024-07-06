/**
 * @file Stage.java
 * @brief ステージクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.camera.Camera;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.AssassinWolf;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.BirdMan;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.GoldWarrior;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.HellishestKing;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.Mage;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.Succubus;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.Warrior;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.Wolf;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.BlackGhost;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.GhostPumpkin;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.GreenPumpkin;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.KingGhost;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.PumpKing;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.Pumpkin;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.WhiteGhost;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;
import info.u6182.mygdx.game.myactiongame.substance.item.ClearItem;
import info.u6182.mygdx.game.myactiongame.substance.item.GoalItem;
import info.u6182.mygdx.game.myactiongame.substance.item.Item;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class Stage
 * @brief ステージクラス
 */
public class Stage {

    //! ボスステージでない
    public static final int BOSS_STAGE_NOT = 0;

    //! ボスステージ(パンプキング戦)
    public static final int BOSS_STAGE_PUMPKING = 1;

    //! ボスステージ(アサシンウルフ戦)
    public static final int BOSS_STAGE_ASSASINWOFL = 2;

    //! ボスステージ(非道の王戦)
    public static final int BOSS_STAGE_HELLISHESTKING = 3;

    //! ボス戦BGM未再生
    private final int BEGIN_BGM_NOT = 0;

    //! ボス戦BGM再生開始
    private final int BEGIN_BGM_PLAY = 1;

    //! ボス戦BGM再生中
    private final int BEGIN_BGM_PLAYING = 2;

    //! ボス戦BGM再生終了
    private final int BEGIN_BGM_PLAYEND = 3;

    //!アイテムボックスのステージ番号
    private final int ITEMBOX_NUMBER = 2;

    //! アイテムボックスの名声スコア
    private final int ITEMBOX_FAMESCORE = 10;

    //! ステージの矩形
    private CustomRectangle chipRect;

    //! ステージの左側矩形
    private CustomRectangle leftRect;

    //! ステージの右側矩形
    private CustomRectangle rightRect;

    //! ステージの上側矩形
    private CustomRectangle topRect;

    //! ステージの下側矩形
    private CustomRectangle buttomRect;

    //! ゲーム開始時のプレイヤーの座標
    private Vector2 playerPos;

    //! ゲーム開始時のカメラの座標
    private Vector2 cameraPos;

    //! ステージBGMパス
    private String bgmPath;

    //! ステージ背景パス
    private String backGroundPath;

    //! アナザーストーリーフラグ
    private boolean bAnotherStory;

    //! ボスステージ
    private int bossStage;

    //! ボスの戦闘開始BGM状態の初期化
    private int bossBeginBGMState;

    //! ステージの位置情報
    private byte[] chipData;

    //! 敵の位置情報
    private byte[] enemyData;

    //! アイテムの位置情報
    private byte[] itemData;

    //! 現在のステージ番号
    private int stageNo;

    //! ステージの列の数
    private int xCount;

    //! ステージの行の数
    private int yCount;

    //! ステージチップのサイズ
    private int chipSize;

    //! 衝突判定のオフセット
    private final float COLLISIN_OFFSET = 15f;

    /**
     * @brief コンストラクタ
     */
    public Stage(){

        //ステージの矩形の初期化
        this.chipRect = new CustomRectangle(0,0,0,0);

        //ステージの左側矩形の初期化
        this.leftRect = new CustomRectangle(0,0,0,0);

        //ステージの右側矩形の初期化
        this.rightRect = new CustomRectangle(0,0,0,0);

        //ステージの上側矩形の初期化
        this.topRect = new CustomRectangle(0,0,0,0);

        //ステージの下側矩形の初期化
        this.buttomRect = new CustomRectangle(0,0,0,0);

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = GameElement.getGameElement().isAnotherStory();

        //ボスの戦闘開始BGM状態の初期化
        this.bossBeginBGMState = BEGIN_BGM_NOT;

        this.bossStage = BOSS_STAGE_NOT;

        //現在のステージ番号の初期化
        this.stageNo = GameElement.getGameElement().getSelectStageNo();

    }

    /**
     * @brief ステージ情報の読み込み
     */
    public void load(){

        //ステージ情報の読み込み
        FileHandle fileHandle = Gdx.files.internal(GameElement.getGameElement().getStagePath());

        //文字列情報に格納
        String info = fileHandle.readString();

        //テンプ用
        String[] tempInfoSplit = info.split("[' ',\n\r]");

        List<String> infoSplit = new ArrayList<>();

        //ステージ情報の格納
        for(String str : tempInfoSplit){

            if(str.length() == 0){

                continue;

            }

            infoSplit.add(str);

        }

        String str;

        str = infoSplit.remove(0);

        float px = Float.parseFloat(str);

        str = infoSplit.remove(0);

        float py = Float.parseFloat(str);

        //プレイヤーの開始時の座標の初期化
        this.playerPos = new Vector2(px, py);

        str = infoSplit.remove(0);

        px = Float.parseFloat(str);

        str = infoSplit.remove(0);

        py = Float.parseFloat(str);

        //ゲーム開始時のカメラの座標の初期化
        this.cameraPos = new Vector2(px, py);

        //チップサイズの読み込み
        str = infoSplit.remove(0);

        this.chipSize = Integer.parseInt(str);

        //ワールドサイズXの読み込み
        str = infoSplit.remove(0);

        this.xCount = Integer.parseInt(str);

        //ワールドサイズYの読み込み
        str = infoSplit.remove(0);

        this.yCount = Integer.parseInt(str);

        //ワールドの確保
        this.chipData = new byte[this.xCount * this.yCount];

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                //ワールドのステージの読み込み
                str = infoSplit.remove(0);

                this.chipData[((this.yCount - 1) - y) * this.xCount + x] = Byte.parseByte(str);

            }

        }

        //敵情報の初期化
        this.enemyData = new byte[this.xCount * this.yCount];

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                //敵の読み込み
                str = infoSplit.remove(0);

                this.enemyData[((this.yCount - 1) - y) * this.xCount + x] = Byte.parseByte(str);

            }

        }

        //アイテム情報の初期化
        this.itemData = new byte[this.xCount * this.yCount];

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                //アイテムの読み込み
                str = infoSplit.remove(0);

                this.itemData[((this.yCount - 1) - y) * this.xCount + x] = Byte.parseByte(str);

            }

        }

    }

    /**
     * @brief 敵とアイテムの初期化を行う
     * @param enemyList 敵リスト
     * @param itemList アイテムリスト
     * @param spriteBatch スプライトバッチ
     */
    public void initialize(List<Enemy> enemyList, List<Item> itemList, CustomSpriteBatch spriteBatch){

        //敵と背景の初期化とBGMの再生
        if(this.bAnotherStory){

            anotherEnemyInitialize(enemyList);

            switch (this.stageNo){

                case GameElement.STAGE1:

                    this.bgmPath = SoundInfo.BGM_ANOTHERSTAGE1;

                    this.backGroundPath = TextureInfo.TEXTURE_ANOTHERSTAGE1BACKGROUND;

                    break;

                case GameElement.STAGE2:

                    this.bgmPath = SoundInfo.BGM_ANOTHERSTAGE2;

                    this.backGroundPath = TextureInfo.TEXTURE_ANOTHERSTAGE2BACKGROUND;

                    this.bossStage = BOSS_STAGE_ASSASINWOFL;

                    break;

                case GameElement.STAGE3:

                    this.bgmPath = SoundInfo.BGM_ANOTHERSTAGE3;

                    this.backGroundPath = TextureInfo.TEXTURE_ANOTHERSTAGE3BACKGROUND;

                    this.bossStage = BOSS_STAGE_HELLISHESTKING;

                    break;

            }

        }else{

            enemyInitialize(enemyList);

            switch (this.stageNo){

                case GameElement.STAGE1:

                    this.bgmPath = SoundInfo.BGM_STAGE;

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE1BACKGROUND;

                    break;

                case GameElement.STAGE2:

                    this.bgmPath = SoundInfo.BGM_STAGE2;

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE2BACKGROUND;

                    break;

                case GameElement.STAGE3:

                    this.bgmPath = SoundInfo.BGM_STAGE3;

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE3BACKGROUND;

                    break;

                case GameElement.STAGE4:

                    this.bgmPath = SoundInfo.BGM_STAGE4;

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE4BACKGROUND;

                    break;

                case GameElement.STAGE5:

                    this.bgmPath = SoundInfo.BGM_STAGE5;

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE5BACKGROUND;

                    break;

                case GameElement.STAGE6:

                    this.bgmPath = SoundInfo.BGM_BOSS;

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE6BACKGROUND;

                    this.bossStage = BOSS_STAGE_PUMPKING;

                    break;

            }

        }

        if(this.bossStage != BOSS_STAGE_NOT){

            this.bossBeginBGMState = BEGIN_BGM_PLAY;

        }

        for(Enemy enemy : enemyList){

            enemy.getAnime().setSpriteBatch(spriteBatch);

        }

        //アイテムの初期化
        itemInitialize(itemList, spriteBatch);

    }

    /**
     * @brief 敵の初期化を行う
     * @param enemyList 敵リスト
     */
    public void enemyInitialize(List<Enemy> enemyList){

        //現在の敵の要素番号
        int no = 0;

        //敵テクスチャ
        Texture texture;

        //敵の出現座標
        Vector2 pos = new Vector2();

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                int type = this.enemyData[y * this.xCount + x] - 1;

                //0未満ならその位置に存在しないので飛ばす
                if(type < 0){

                    continue;

                }

                pos.set(x * this.chipSize, y * this.chipSize);

                //敵の生成
                switch (type){

                    case GameElement.ENEMY_PUMPKIN:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKIN);

                        enemyList.add(new Pumpkin(texture, pos, type));

                        break;

                    case GameElement.ENEMY_GREENPUMPKIN:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GREENPUMPKIN);

                        enemyList.add(new GreenPumpkin(texture, pos, type));

                        break;

                    case GameElement.ENEMY_GHOSTPUMPKIN:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GHOSTPUMPKIN);

                        enemyList.add(new GhostPumpkin(texture, pos, type));

                        break;

                    case GameElement.ENEMY_WHITEGHOST:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_WHITEGHOST);

                        enemyList.add(new WhiteGhost(texture, pos, type));

                        break;

                    case GameElement.ENEMY_BLACKGHOST:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BLACKGHOST);

                        enemyList.add(new BlackGhost(texture, pos, type));

                        break;

                    case GameElement.ENEMY_KINGGHOST:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KINGGHOST);

                        enemyList.add(new KingGhost(texture, pos, type));

                        break;

                    case GameElement.ENEMY_PUMPKING:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKING);

                        enemyList.add(new PumpKing(texture, pos, type));

                        break;

                }

            }

        }

    }

    /**
     * @brief アナザーストーリーの敵の初期化を行う
     * @param enemyList 敵リスト
     */
    public void anotherEnemyInitialize(List<Enemy> enemyList){

        //現在の敵の要素番号
        int no = 0;

        //敵テクスチャ
        Texture texture;

        //敵の出現座標
        Vector2 pos = new Vector2();

        String path = null;

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                int type = this.enemyData[y * this.xCount + x] - 1;

                //0未満ならその位置に存在しないので飛ばす
                if(type < 0){

                    continue;

                }

                pos.set(x * this.chipSize, y * this.chipSize);

                //敵の生成
                switch (type){

                    case GameElement.ENEMY_FIREBIRDMAN:

                        if(path == null){

                            path = TextureInfo.TEXTURE_FIREBIRDMAN;

                        }

                    case GameElement.ENEMY_ICEBIRDMAN:

                        if(path == null){

                            path = TextureInfo.TEXTURE_ICEBIRDMAN;

                        }

                    case GameElement.ENEMY_WOODBIRDMAN:

                        if(path == null){

                            path = TextureInfo.TEXTURE_WOODBIRDMAN;

                        }

                        texture = TextureManager.getTextureManager().getTexture(path);

                        enemyList.add(new BirdMan(texture, pos, type, no));

                        path = null;

                        break;

                    case GameElement.ENEMY_WARRIOR:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_WARRIOR);

                        enemyList.add(new Warrior(texture, pos, type, no));

                        break;

                    case GameElement.ENEMY_GOLDWARRIOR:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GOLDWARRIOR);

                        enemyList.add(new GoldWarrior(texture, pos, type, no));

                        break;

                    case GameElement.ENEMY_SUCCUBUS:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_SUCCUBUS);

                        enemyList.add(new Succubus(texture, pos, type, no));

                        break;

                    case GameElement.ENEMY_MAGE:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_MAGE);

                        enemyList.add(new Mage(texture, new StageInfo(this.chipData, this.chipSize, this.xCount, this.yCount), pos, type, no));

                        break;

                    case GameElement.ENEMY_WOLFRIGHT:
                    case GameElement.ENEMY_WOLFLEFT:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_WOLF);

                        enemyList.add(new Wolf(texture, pos, type, no));

                        break;

                    case GameElement.ENEMY_ASSASSINWOLF:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_ASSASSINWOLF);

                        enemyList.add(new AssassinWolf(texture, pos, type, no));

                        break;

                    case GameElement.ENEMY_HELLISHESTKING:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_OBSESSION);

                        enemyList.add(new HellishestKing(texture, pos, type, no));

                        break;

                }

            }

        }

    }

    /**
     * @brief アイテムの初期化を行う
     * @param itemList アイテムリスト
     * @param spriteBatch スプライトバッチ
     */
    public void itemInitialize(List<Item> itemList, CustomSpriteBatch spriteBatch){

        //現在のアイテムの要素番号
        int no = 0;

        //アイテムテクスチャ
        Texture texture;

        //アイテムの出現座標
        Vector2 pos = new Vector2();

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                int type = this.itemData[y * this.xCount + x] - 1;

                //0未満ならその位置に存在しないので飛ばす
                if(type < 0){

                    continue;

                }

                pos.set(x * this.chipSize, y * this.chipSize);

                String path = null;

                //アイテムの生成
                switch (type){

                    case GameElement.ITEM_SILVERGOAL:

                        if(path == null){

                            path = TextureInfo.TEXTURE_SILVERGOAL;

                        }

                    case GameElement.ITEM_GOLDGOAL:

                        if(path == null){

                            path = TextureInfo.TEXTURE_GOLDGOAL;

                        }

                    case GameElement.ITEM_ANOTHERGOAL:

                        if(path == null){

                            path = TextureInfo.TEXTURE_ANOTHERGOAL;

                        }

                    case GameElement.ITEM_ANOTHERBRANCHGOAL:

                        if(path == null){

                            path = TextureInfo.TEXTURE_ANOTHERBRANCHGOAL;

                        }

                    case GameElement.ITEM_ASSASSINCLEARGOAL:

                        if(path == null){

                            path = TextureInfo.TEXTURE_ANOTHERGOAL;

                        }

                        texture = TextureManager.getTextureManager().getTexture(path);

                        itemList.add(new GoalItem(texture, pos, type));

                        path = null;

                        break;

                    case GameElement.ITEM_CLEARGOAL:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_CLEAR);

                        itemList.add(new ClearItem(spriteBatch, texture, pos, type));

                        break;

                }

            }

        }

    }

    /**
     * @brief ステージの衝突判定
     * @param rect キャラクターの矩形
     * @param buried 衝突後の補正値
     * @return 衝突が発生したかどうか
     */
    public boolean collision(CustomRectangle rect, Vector2 buried){

        boolean re = false;

        int lc = (int)rect.left / this.chipSize;

        int rc = (int)rect.right / this.chipSize;

        int tc = (int)rect.top / this.chipSize;

        int bc = (int)rect.buttom / this.chipSize;

        if(lc < 0){

            lc = 0;

        }

        if(bc < 0){

            bc = 0;

        }


        if(rc >= this.xCount){

            rc = this.xCount - 1;

        }

        if(tc >= this.yCount){

            tc = this.yCount - 1;

        }

        float collisionOffsetX = COLLISIN_OFFSET;

        float collisionOffsetY = COLLISIN_OFFSET;

        if(rect.width < COLLISIN_OFFSET * 2){

            collisionOffsetX = rect.width * 0.5f - 2;

        }

        if(rect.height < COLLISIN_OFFSET * 2){

            collisionOffsetY = rect.height * 0.5f - 2;

        }

        for(int y = bc;y <= tc;y++){

            for(int x = lc;x <= rc;x++){

                byte cn = (byte) (this.chipData[y * this.xCount + x] - 1);

                if(cn < 0){

                    continue;

                }

                //ステージ矩形
                this.chipRect.set(x * this.chipSize, y * this.chipSize, this.chipSize, this.chipSize);

                //キャラクター下側矩形
                this.buttomRect.setCustomRect(rect.left + collisionOffsetX, rect.buttom, rect.right - collisionOffsetX, rect.buttom + 1);

                //ステージとキャラクターの下側の衝突判定
                if(this.chipRect.overlaps(this.buttomRect)){

                    re = true;

                    buried.y += this.chipRect.top - this.buttomRect.buttom;

                    rect.add(0f, buried.y, 0f, buried.y);

                }

                //キャラクターの左側矩形
                this.leftRect.setCustomRect(rect.left, rect.buttom + collisionOffsetY, rect.left + 1, rect.top - collisionOffsetY);

                //キャラクターの右側矩形
                this.rightRect.setCustomRect(rect.right - 1, rect.buttom + collisionOffsetY, rect.right, rect.top - collisionOffsetY);

                if(this.chipRect.overlaps(this.leftRect)){

                    re = true;

                    buried.x += this.chipRect.right - this.leftRect.left;

                    rect.add(buried.x, 0f, buried.x, 0f);

                }else if(this.chipRect.overlaps(this.rightRect)){

                    re = true;

                    buried.x += this.chipRect.left - this.rightRect.right;

                    rect.add(buried.x, 0f, buried.x, 0f);

                }

                this.topRect.setCustomRect(rect.left + collisionOffsetX, rect.top - 1, rect.right - collisionOffsetX, rect.top);

                if(this.chipRect.overlaps(this.topRect)){

                    re = true;

                    buried.y += this.chipRect.buttom - this.topRect.top;

                    rect.add(0f, buried.y, 0f, buried.y);

                }

            }

        }

        return re;

    }

    /**
     * @brief アイテムボックスとの衝突判定
     * @param player プレイヤー
     * @return 衝突が発生したかどうか
     */
    public boolean collisionItemStage(Player player){

        boolean re = false;

        CustomRectangle rect = player.getRect();

        int lc = (int)rect.left / this.chipSize;

        int rc = (int)rect.right / this.chipSize;

        int tc = (int)rect.top / this.chipSize;

        int bc = (int)rect.buttom / this.chipSize;

        if(lc < 0){

            lc = 0;

        }

        if(bc < 0){

            bc = 0;

        }


        if(rc >= this.xCount){

            rc = this.xCount - 1;

        }

        if(tc >= this.yCount){

            tc = this.yCount - 1;

        }



        for(int y = bc;y <= tc;y++){

            for(int x = lc;x <= rc;x++){

                byte cn = (byte) (this.chipData[y * this.xCount + x] - 1);

                //ステージ番号がアイテムボックス以外の場合飛ばす
                if(cn != ITEMBOX_NUMBER){

                    continue;

                }

                //ステージ矩形
                this.chipRect.set(x * this.chipSize, y * this.chipSize, this.chipSize, this.chipSize);

                this.topRect.setCustomRect(rect.left, rect.top - 1, rect.right, rect.top);

                if(this.chipRect.overlaps(this.topRect)){

                    if(player.getMove().y > 0){

                        re = true;

                        this.chipData[y * this.xCount + x] = 0;

                        player.setFameScore(ITEMBOX_FAMESCORE);

                        break;

                    }

                }

            }

        }

        return re;

    }

    /**
     * @brief ステージの更新処理を行う
     * @param bChange ステージ変更フラグ
     */
    public void update(boolean bChange){

        if(this.bossBeginBGMState == BEGIN_BGM_PLAY){

            //ボスステージの場合
            switch (this.bossStage){

                case BOSS_STAGE_PUMPKING:

                    SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_BOSSBEGIN, false);

                    break;

                case BOSS_STAGE_ASSASINWOFL:

                    SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_ASSASSINWOLFBEGIN, false);

                    break;

                case BOSS_STAGE_HELLISHESTKING:

                    SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_HELLISHESTKINGBEGIN, false);

                    break;

            }

            this.bossBeginBGMState = BEGIN_BGM_PLAYING;

        }

        if(this.bossBeginBGMState == BEGIN_BGM_PLAYING){

            if(!SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_BOSSBEGIN) && !SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_ASSASSINWOLFBEGIN) && !SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_HELLISHESTKINGBEGIN)){

                this.bossBeginBGMState = BEGIN_BGM_PLAYEND;

            }

        }

        //ボス戦直前のBGMが再生されていない場合ステージBGMを再生
        if(!bChange && !SoundManager.getSoundManager().isBGMPlaying(this.bgmPath)){

            if(this.bossStage != BOSS_STAGE_NOT && this.bossBeginBGMState == BEGIN_BGM_PLAYING){

                return;

            }

            SoundManager.getSoundManager().bgmPlay(this.bgmPath);

        }

    }

    /**
     * @brief ステージの描画処理を行う
     * @param spriteBatch スプライトバッチ
     * @param camera カメラ
     */
    public void render(CustomSpriteBatch spriteBatch, Camera camera){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 scroll = GameElement.getGameElement().getScroll();

        Texture backGround = TextureManager.getTextureManager().getTexture(this.backGroundPath);

        int hn = backGround.getHeight();
        int wn = backGround.getWidth();

        int cameraPosX = (int)camera.getPosLeft();
        int cameraPosY = (int)camera.getPosButtom();

        //ステージ背景の描画
        for (float y = (int)-(scroll.y * 0.25f) % hn + cameraPosY;y < cameraPosY + viewSize.y + hn;y += hn) {

            for(int x = (int)-(scroll.x * 0.25f) % wn + cameraPosX; x < cameraPosX + viewSize.x + wn;x += wn){

                spriteBatch.draw(backGround, x, y);

            }


        }

        Texture chipTexture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_STAGECHIP);

        int lc = cameraPosX / this.chipSize;

        int rc = (int)(cameraPosX + viewSize.x) / this.chipSize;

        int tc = cameraPosY / this.chipSize;

        int bc = (int)(cameraPosY + viewSize.y) / this.chipSize;

        if(lc < 0){

            lc = 0;

        }

        if(tc < 0){

            tc = 0;

        }


        if(rc >= this.xCount){

            rc = this.xCount - 1;

        }

        if(bc >= this.yCount){

            bc = this.yCount - 1;

        }


        int tcx = chipTexture.getWidth() / this.chipSize;

        //ステージの描画
        for(int y = tc;y <= bc;y++){

            for(int x = lc;x <= rc;x++){

                byte cn = (byte)(this.chipData[y * this.xCount + x] - 1);

                //0未満ならその位置に存在しないので飛ばす
                if(cn < 0){

                    continue;

                }

                spriteBatch.draw(chipTexture, x * this.chipSize, y * this.chipSize, this.chipSize * (cn % tcx)
                        , this.chipSize * (cn / tcx), this.chipSize, this.chipSize);

            }

        }

    }

    /**
     * @brief プレイヤーの初期座標の取得を行う
     * @return プレイヤーの初期座標
     */
    public Vector2 getPlayerPos(){

        return this.playerPos;

    }

    /**
     * @brief カメラの初期座標の取得を行う
     * @return カメラの初期座標
     */
    public Vector2 getCameraPos(){

        return this.cameraPos;

    }

    /**
     * @brief ステージBGMのパスの取得を行う
     * @return ステージBGMのパス
     */
    public String getBgmPath(){

        return this.bgmPath;

    }

    /**
     * @brief ボスステージの種別の取得を行う
     * @return ボスステージの種別
     */
    public int getBossStage(){

        return this.bossStage;

    }

    /**
     * @brief カメラリセットが必要かどうかの取得を行う
     * @return カメラリセットが必要かどうか
     */
    public boolean isCameraReset(){

        if(this.bossStage == BOSS_STAGE_HELLISHESTKING && this.bossBeginBGMState == BEGIN_BGM_PLAYEND){

            return true;

        }

        return false;

    }

    /**
     * @brief ステージチップのサイズの取得を行う
     * @return ステージチップのサイズ
     */
    public int getChipSize(){

        return this.chipSize;

    }

    /**
     * @brief ステージの高さの取得を行う
     * @return ステージの高さ
     */
    public float getStageHeight(){

        return this.yCount * this.chipSize;

    }

    /**
     * @brief ステージの幅の取得を行う
     * @return ステージの幅
     */
    public float getStageWidth(){

        return this.xCount * this.chipSize;

    }

}
