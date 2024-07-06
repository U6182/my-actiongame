/**
 * @file AnimationStage.java
 * @brief アニメーションステージの管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.texturemanager.TextureManager;

/**
 * @class AnimationStage
 * @brief アニメーションステージを管理するクラス
 */
public class AnimationStage {

    //! 敵のサイズ
    protected Vector2 enemySize;

    //! ステージ情報
    protected byte[] chipData;

    //! 敵情報
    protected byte[] enemyData;

    //! ステージチップ数X
    protected int xCount;

    //! ステージチップ数Y
    protected int yCount;

    //! ステージチップサイズ
    protected int chipSize;

    protected float stageHeight;

    /**
     * @brief ステージデータの読み込みを行う
     * @param path ステージデータのファイルパス
     */
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

                int xx = 0;

                if(Integer.parseInt(str) > 0){

                xx = 1;
                }
                this.enemyData[((this.yCount - 1) - y) * this.xCount + x] = Byte.parseByte(str);

            }

        }

    }

    /**
     * @brief アニメの衝突処理を行う
     * @param anime アニメーションオブジェクト
     */
    public void collision(Anime anime){

        if(anime.getPos().y <= stageHeight){

            anime.setJump(false);

            anime.getMove().y = 0f;

            anime.getPos().y = this.stageHeight;

        }

    }

    /**
     * @brief ステージの描画処理を行う
     * @param spriteBatch スプライトバッチ
     * @param backGround 背景テクスチャ
     */
    public void render(CustomSpriteBatch spriteBatch, Texture backGround){

        Vector2 viewPortSize = GameElement.getGameElement().getViewPortSize();

        int tw = backGround.getWidth();

        int th = backGround.getHeight();

        //背景の描画
        for(int y = 0;y < viewPortSize.y;y += th){

            for(int x = 0;x < viewPortSize.x;x += tw){

                spriteBatch.draw(backGround, x, y);

            }

        }

        Texture chipTexture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_STAGECHIP);

        int tcx = chipTexture.getWidth() / this.chipSize;

        //ステージの描画
        for(int y = 0;y < this.yCount;y++){

            for(int x = 0;x < this.xCount;x++){

                byte cn = (byte)(this.chipData[y * this.xCount + x] - 1);

                if(cn < 0){

                    continue;

                }

                spriteBatch.draw(chipTexture, x * this.chipSize, y * this.chipSize, this.chipSize * (cn % tcx)
                        , this.chipSize * (cn / tcx), this.chipSize, this.chipSize);

            }

        }

    }
}
