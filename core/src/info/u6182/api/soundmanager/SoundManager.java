/**
 * @file SoundManager.java
 * @brief サウンドマネージャクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.soundmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.u6182.api.AssetInfo;

/**
 * @class SoundManager
 * @brief サウンドマネージャクラス
 * @details ゲーム内のサウンドおよびBGMの管理を行うクラス
 */
public class SoundManager {

    //! サウンドマネージャのインスタンス
    private static SoundManager soundManager = new SoundManager();

    //! サウンドリスト
    private Map<String, Sound> soundList = new HashMap<>();

    //! 再生状態フラグ
    private Map<String, Boolean> bPlay = new HashMap<>();

    //! BGM
    private Music bgm;

    //! サブBGM
    private Music subBgm;

    //! BGMパス
    private String bgmPath;

    //! サブBGMパス
    private String subBGMPath;

    //! サウンドオフフラグ
    private boolean bSoundOFF;

    /**
     * @brief コンストラクタ
     * @details サウンドマネージャの初期化を行う
     */
    private SoundManager(){

        this.bSoundOFF = false;

    }

    /**
     * @brief load
     * @param SoundInfo サウンド情報
     * @details サウンドリストのロードを行う
     */
    public void load(AssetInfo SoundInfo){

        ArrayList<String> list = SoundInfo.getInfo();

        int len = list.size();

        for(int i = 0;i < len;i++){

            Sound sound = Gdx.audio.newSound(Gdx.files.internal(list.get(i)));

            this.soundList.put(list.get(i), sound);

        }

    }

    /**
     * @brief release
     * @details サウンドリストの解放を行う
     */
    public void release(){

        for(Sound sound : this.soundList.values()){

            sound.dispose();

        }

        this.soundList.clear();

        if(this.bgm != null){

            this.bgm.dispose();

        }

        if(this.subBgm != null){

            this.subBgm.dispose();

        }

    }

    /**
     * @brief bgmPlay
     * @param path BGMのパス
     * @details BGMの再生を行う
     */
    public void bgmPlay(String path){

        this.bgm = Gdx.audio.newMusic(Gdx.files.internal(path));

        this.bgm.setLooping(true);

        this.bgm.play();

        this.bgmPath = path;

        if(this.bSoundOFF){

            this.bgm.setVolume(0);

        }

    }

    /**
     * @brief subBGMPlay
     * @param path サブBGMのパス
     * @param bLoop ループフラグ
     * @details サブBGMの再生を行う
     */
    public void subBGMPlay(String path, boolean bLoop){

        this.subBgm = Gdx.audio.newMusic(Gdx.files.internal(path));

        this.subBgm.setLooping(bLoop);

        this.subBgm.play();

        this.subBGMPath = path;

        if(this.bSoundOFF){

            this.subBgm.setVolume(0);

        }

    }

    /**
     * @brief bgmPlay
     * @param path BGMのパス
     * @param bLoop ループフラグ
     * @details BGMの再生を行う
     */
    public void bgmPlay(String path, boolean bLoop){

        this.bgm = Gdx.audio.newMusic(Gdx.files.internal(path));

        this.bgm.setLooping(bLoop);

        this.bgm.play();

        this.bgmPath = path;

        if(this.bSoundOFF){

            this.bgm.setVolume(0);

        }

    }

    /**
     * @brief play
     * @param path サウンドのパス
     * @details サウンドの再生を行う
     */
    public void play(String path){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).play();

    }

    /**
     * @brief play
     * @param path サウンドのパス
     * @param volume 音量
     * @details 指定された音量でサウンドの再生を行う
     */
    public void play(String path, float volume){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).play(volume);

    }

    /**
     * @brief play
     * @param path サウンドのパス
     * @param volume 音量
     * @param pitch ピッチ
     * @param pan パン
     * @details 指定された音量、ピッチ、パンでサウンドの再生を行う
     */
    public void play(String path, float volume, float pitch, float pan){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).play(volume, pitch, pan);

    }

    /**
     * @brief loopPlay
     * @param path サウンドのパス
     * @details サウンドのループ再生を行う
     */
    public void loopPlay(String path){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).loop();

        this.soundList.get(path).play();

    }

    /**
     * @brief stop
     * @param path サウンドのパス
     * @details サウンドの停止を行う
     */
    public void stop(String path){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).stop();

    }

    /**
     * @brief allStop
     * @details 全てのサウンドの停止を行う
     */
    public void allStop(){

        for(Sound sound : this.soundList.values()){

            sound.stop();

        }

        if(this.bgm != null){

            this.bgm.stop();

        }

    }

    /**
     * @brief bgmStop
     * @details BGMの停止を行う
     */
    public void bgmStop(){

        if(this.bgm != null){

            this.bgm.stop();

        }

    }

    /**
     * @brief subBGMStop
     * @details サブBGMの停止を行う
     */
    public void subBGMStop(){

        if(this.subBgm != null){

            this.subBgm.stop();

        }

    }

    /**
     * @brief pause
     * @param path サウンドのパス
     * @details サウンドの一時停止を行う
     */
    public void pause(String path){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).pause();

    }

    /**
     * @brief allPause
     * @details 全てのサウンドの一時停止を行う
     */
    public void allPause(){

        if(this.bSoundOFF){

            return;

        }

        for(Sound sound : this.soundList.values()){

            sound.pause();

        }

        this.bgm.pause();

    }

    /**
     * @brief resume
     * @param path サウンドのパス
     * @details サウンドの再開を行う
     */
    public void resume(String path){

        if(this.bSoundOFF){

            return;

        }

        this.soundList.get(path).resume();

    }

    /**
     * @brief allResume
     * @details 全てのサウンドの再開を行う
     */
    public void allResume(){

        if(this.bSoundOFF){

            return;

        }

        for(Sound sound : this.soundList.values()){

            sound.resume();

        }

        this.bgm.play();

    }

    /**
     * @brief setBgmLoop
     * @param bLoop ループフラグ
     * @details BGMのループ設定を行う
     */
    public void setBgmLoop(boolean bLoop){

        if(this.bSoundOFF){

            return;

        }

        this.bgm.setLooping(bLoop);

    }

    /**
     * @brief setSoundOFF
     * @param bSoundOFF サウンドオフフラグ
     * @details サウンドオフの設定を行う
     */
    public void setSoundOFF(boolean bSoundOFF){

        this.bSoundOFF = bSoundOFF;

    }

    /**
     * @brief getSound
     * @param path サウンドのパス
     * @return Sound サウンドオブジェクト
     * @details 指定されたパスのサウンドの取得を行う
     */
    public Sound getSound(String path){

        return this.soundList.get(path);

    }

    /**
     * @brief isBGMPlaying
     * @param path BGMのパス
     * @return boolean BGMが再生中かどうか
     * @details 指定されたパスのBGMが再生中かどうかの判定を行う
     */
    public boolean isBGMPlaying(String path){

        if(this.bgmPath == null || !this.bgmPath.equals(path)){

            return false;

        }

        return this.bgm.isPlaying();

    }

    /**
     * @brief isSubBGMPlaying
     * @param path サブBGMのパス
     * @return boolean サブBGMが再生中かどうか
     * @details 指定されたパスのサブBGMが再生中かどうかの判定を行う
     */
    public boolean isSubBGMPlaying(String path){

        if(this.subBGMPath == null || !this.subBGMPath.equals(path)){

            return false;

        }

        return this.subBgm.isPlaying();

    }

    /**
     * @brief isSoundOFF
     * @return boolean サウンドオフかどうか
     * @details サウンドオフかどうかの判定を行う
     */
    public boolean isSoundOFF(){

        return this.bSoundOFF;

    }

    /**
     * @brief getSoundManager
     * @return SoundManager サウンドマネージャのインスタンス
     * @details サウンドマネージャインスタンスの取得を行う
     */
    public static SoundManager getSoundManager(){

        return soundManager;

    }

}
