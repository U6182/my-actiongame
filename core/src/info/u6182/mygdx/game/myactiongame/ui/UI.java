/**
 * @file UI.java
 * @brief ゲーム内のUIクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.AnotherPlayer;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.camera.Camera;
import info.u6182.api.fontmanager.FontManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class UI
 * @brief ゲーム内のUIクラス
 */
public class UI {

    private String[] ENEMY = {TextureInfo.TEXTURE_PUMPKIN, TextureInfo.TEXTURE_GREENPUMPKIN, TextureInfo.TEXTURE_GHOSTPUMPKIN, TextureInfo.TEXTURE_WHITEGHOST, TextureInfo.TEXTURE_BLACKGHOST, TextureInfo.TEXTURE_KINGGHOST};;

    //! アナザーストーリーフラグ
    private boolean bAnotherStory;

    //! プレイヤー情報のオフセットX
    private final float PLAYER_INFOOFFSETX = 130f;

    //! プレイヤー情報のオフセットY
    private final float PLAYER_INFOOFFSETY = 10f;

    /**
     * @brief コンストラクタ
     */
    public UI(){

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = GameElement.getGameElement().isAnotherStory();

    }

    /**
     * @brief 描画を行う
     * @param player プレイヤーオブジェクト
     * @param spriteBatch スプライトバッチ
     * @param uiCamera UIカメラ
     */
    public void render(Player player, CustomSpriteBatch spriteBatch, Camera uiCamera){

        //プレイヤーの情報の描画
        if(this.bAnotherStory){

            AnotherPlayer anotherPlayer = null;

            if(player instanceof AnotherPlayer){

                anotherPlayer = (AnotherPlayer)player;

            }

            anotherInfoRender(anotherPlayer, spriteBatch, uiCamera);

        }else{

            infoRender(player, uiCamera);

        }

    }

    /**
     * @brief プレイヤー情報の描画を行う
     * @param player プレイヤーオブジェクト
     * @param uiCamera UIカメラ
     */
    private void infoRender(Player player, Camera uiCamera){

        Color guiltColor = player.getGuilt() != 0 ? new Color(0xff0000ff) : Color.GREEN;

        float infoPosY = uiCamera.viewportHeight - PLAYER_INFOOFFSETY;

        FontManager.getFontmanager().render("罪悪度 " + player.getGuilt(), guiltColor, 10f, infoPosY);

        if(player.isObsession()){

            FontManager.getFontmanager().render("強迫観念 : ジャンプ" + player.getObsessionCount() + "回!!", new Color(0xff0000ff), PLAYER_INFOOFFSETX - PLAYER_INFOOFFSETX * 0.3f, infoPosY);

        }else{

            FontManager.getFontmanager().render("強迫観念 : ", Color.GREEN, PLAYER_INFOOFFSETX - PLAYER_INFOOFFSETX * 0.3f, infoPosY);

        }

        FontManager.getFontmanager().render("地位 " + GameElement.STATUS_NAME[player.getStatus()], Color.YELLOW, PLAYER_INFOOFFSETX * 2, infoPosY);

        FontManager.getFontmanager().render("名声 " + player.getSumShowFameScore() + ": " + player.getShowFameScore(), Color.GREEN, PLAYER_INFOOFFSETX * 3  - PLAYER_INFOOFFSETX * 0.3f, infoPosY);

    }

    /**
     * @brief アナザープレイヤー情報の描画を行う
     * @param anotherPlayer アナザープレイヤーオブジェクト
     * @param spriteBatch スプライトバッチ
     * @param uiCamera UIカメラ
     */
    private void anotherInfoRender(AnotherPlayer anotherPlayer, CustomSpriteBatch spriteBatch, Camera uiCamera){

        Texture enemyFrame = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_ENEMYFRAME);

        Texture callPowerBer = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_CALLPOWERBER);

        float posX = callPowerBer.getWidth() + 10;

        float posY = uiCamera.viewportHeight - enemyFrame.getHeight() * 1.5f;

        spriteBatch.draw(enemyFrame, posX, posY);

        for(int i = -1;i < 2;i++){

            int type = anotherPlayer.getEnemyType() + i;

            if(type < 0){

                type = GameElement.ENEMY_COUNT - 2;

            }else if(type > GameElement.ENEMY_COUNT - 2){

                type = 0;

            }

            float alpha = (type == anotherPlayer.getEnemyType()) ? 1f : 0.5f;

            spriteBatch.setColor(1,1,1, alpha);

            Texture enemy = TextureManager.getTextureManager().getTexture(this.ENEMY[type]);

            spriteBatch.draw(enemy, posX + (i + 1) * 32, posY, 0, 0, 32, 32);

        }

        spriteBatch.setColor(1,1,1, 1);

        Texture callPower = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_CALLPOWER);

        spriteBatch.draw(callPower, 73, uiCamera.viewportHeight - 39, callPower.getWidth() * (anotherPlayer.getCallPower() * 0.01f), callPower.getHeight());

        spriteBatch.draw(callPowerBer, 0,uiCamera.viewportHeight - callPowerBer.getHeight());

        FontManager.getFontmanager().render(Integer.toString(anotherPlayer.getCallPower()), new Color(0xff0000ff), 35, uiCamera.viewportHeight - 28);

    }

}
