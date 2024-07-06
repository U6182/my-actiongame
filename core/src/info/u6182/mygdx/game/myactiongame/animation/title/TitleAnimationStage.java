/**
 * @file TitleAnimationStage.java
 * @brief タイトルアニメーションのステージ管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.animation.AnimationStage;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class TitleAnimationStage
 * @brief タイトル画面におけるアニメーションステージを管理するクラス
 */
public class TitleAnimationStage extends AnimationStage {

    /**
     * @brief コンストラクタ
     */
    public TitleAnimationStage(){

        //ステージチップ数Xの初期化
        this.xCount = 0;

        //ステージチップ数Yの初期化
        this.yCount = 0;

        //ステージチップサイズの初期化
        this.chipSize = 0;

        //敵のサイズの初期化
        this.enemySize = new Vector2(32, 32);

        this.stageHeight = 96f;

    }

    /**
     * @brief ステージデータの読み込身を行う
     * @param path ステージデータのファイルパス
     */
    @Override
    public void load(String path){

        //タイトル情報の読み込み
        FileHandle fileHandle = Gdx.files.internal(path);

        //文字列情報に格納
        String info = fileHandle.readString();

        //テンプ用
        String[] tempInfoSplit = info.split("[' ',\n\r]");

        List<String> infoSplit = new ArrayList<>();

        //タイトル情報の格納
        for(String str : tempInfoSplit){

            if(str.length() == 0){

                continue;

            }

            infoSplit.add(str);

        }

        String str;

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

                this.enemyData[y * this.xCount + x] = Byte.parseByte(str);

            }

        }

    }

    /**
     * @brief ステージの初期化を行う
     * @param enemyList 敵キャラクターのリスト
     * @param spriteBatch スプライトバッチ
     */
    public void initialize(List<TitleAnimationEnemy> enemyList, CustomSpriteBatch spriteBatch){

        int no = 0;

        Vector2 pos = new Vector2();

        Texture texture;

        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                byte on = (byte)(this.enemyData[y * this.xCount + x] - 1);

                if(on < 0){

                    continue;

                }

                if(no == 0){

                    texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BLACKGHOST);

                    pos.set(x * this.chipSize + this.enemySize.x, ((this.yCount - 1) - y) * this.chipSize);

                    enemyList.add(new TitleAnimationEnemy());

                    enemyList.get(no++).initialize(texture, pos, this.enemySize, (no + 1 * 5) + 2);

                }else if(no == 1){

                    texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_WHITEGHOST);

                    pos.set(x * this.chipSize - this.enemySize.x, ((this.yCount - 1) - y) * this.chipSize);

                    enemyList.add(new TitleAnimationEnemy());

                    enemyList.get(no++).initialize(texture, pos, this.enemySize, (no + 1 * 5) + 2);

                }else if(no == 2){

                    texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKIN);

                    pos.set(x * this.chipSize + this.enemySize.x, ((this.yCount - 1) - y) * this.chipSize);

                    enemyList.add(new TitleAnimationEnemy());

                    enemyList.get(no++).initialize(texture, pos, this.enemySize, (no + 1 * 5) + 2);

                }

            }

        }

        for(TitleAnimationEnemy enemy : enemyList){

            enemy.getAnime().setSpriteBatch(spriteBatch);

        }

    }

    /**
     * @brief ステージの描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        Texture backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_TITLEBACKBACKGROUND);

        super.render(spriteBatch, backGround);

    }

}