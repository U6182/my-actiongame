/**
 * @file CreditAnimationStage.java
 * @brief クレジットアニメーションのステージ管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.credit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.animation.AnimationStage;

import java.util.List;

import info.u6182.api.texturemanager.TextureManager;

/**
 * @class CreditAnimationStage
 * @brief クレジットアニメーションのステージを管理するクラス
 */
public class CreditAnimationStage extends AnimationStage {

    //! 背景画像のパス
    private String backGroundPath;

    //! ステージ番号
    private int stageNo;

    /**
     * @brief コンストラクタ
     */
    public CreditAnimationStage(){

        this.stageHeight = 64f;

    }

    /**
     * @brief ステージの初期化を行う
     * @param enemyList 敵のリスト
     * @param spriteBatch スプライトバッチ
     * @param bAnotherStory アナザーストーリーフラグ
     * @param bBranchClear 分岐クリアフラグ
     * @param stageNo ステージ番号
     */
    public void initialize(List<CreditAnimationEnemy> enemyList, CustomSpriteBatch spriteBatch, boolean bAnotherStory, boolean bBranchClear, int stageNo) {

        this.stageNo = stageNo;

        int no = 0;

        //敵テクスチャ
        Texture texture = null;

        //敵の出現座標
        Vector2 pos = new Vector2();

        for (int y = 0; y < this.yCount; y++) {

            for (int x = 0; x < this.xCount; x++) {

                byte type = (byte) (this.enemyData[y * this.xCount + x] - 1);

                if (type < 0) {

                    continue;

                }

                pos.set(x * this.chipSize, y * this.chipSize);

                //敵の生成
                switch (type) {

                    case GameElement.ENEMY_PUMPKIN:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKIN);

                        break;

                    case GameElement.ENEMY_GREENPUMPKIN:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GREENPUMPKIN);

                        break;

                    case GameElement.ENEMY_GHOSTPUMPKIN:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GHOSTPUMPKIN);

                        break;

                    case GameElement.ENEMY_WHITEGHOST:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_WHITEGHOST);

                        break;

                    case GameElement.ENEMY_BLACKGHOST:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BLACKGHOST);

                        break;

                    case GameElement.ENEMY_KINGGHOST:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KINGGHOST);

                        break;

                    case GameElement.ENEMY_PUMPKING:

                        texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKING);

                        break;

                }

                enemyList.add(new CreditAnimationEnemy());

                enemyList.get(no++).initialize(spriteBatch, texture, pos, type);

            }

        }

        if (bAnotherStory) {

            this.backGroundPath = bBranchClear ? TextureInfo.TEXTURE_ANOTHERSTAGE1BACKGROUND : TextureInfo.TEXTURE_ANOTHERSTAGE3BACKGROUND;

        } else {

            switch (this.stageNo) {

                case GameElement.STAGE1:

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE1BACKGROUND;

                    break;

                case GameElement.STAGE2:

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE2BACKGROUND;

                    break;

                case GameElement.STAGE3:

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE3BACKGROUND;

                    break;

                case GameElement.STAGE4:

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE4BACKGROUND;

                    break;

                case GameElement.STAGE5:

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE5BACKGROUND;

                    break;

                case GameElement.STAGE6:

                    this.backGroundPath = TextureInfo.TEXTURE_STAGE6BACKGROUND;

                    break;

            }

        }

    }

    /**
     * @brief ステージの描画処理を行う
     * @param spriteBatch スプライトバッチ
     * @param alpha アルファ値
     */
    public void render(CustomSpriteBatch spriteBatch, float alpha){

        Texture backGround = TextureManager.getTextureManager().getTexture(this.backGroundPath);

        spriteBatch.setColor(1,1,1, alpha);

        super.render(spriteBatch, backGround);

    }
}
