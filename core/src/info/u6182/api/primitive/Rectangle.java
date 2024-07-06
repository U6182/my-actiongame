/**
 * @file Rectangle.java
 * @brief 矩形を表すクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.primitive;

import info.u6182.api.vector.Vector2;

/**
 * @class Rectangle
 * @brief 矩形を表すクラス
 */
public class Rectangle {

    //! 左下角の位置を座標
    public final Vector2 lowerLeft;

    //! 幅と高さ
    public float width, height;

    /**
     * @brief コンストラクタ
     * @param x 座標X
     * @param y 座標Y
     * @param width 幅
     * @param height 高さ
     * @details 座標、幅、高さを指定して矩形の初期化を行う
     */
    public Rectangle(float x, float y, float width, float height){

        this.lowerLeft = new Vector2(x, y);

        this.width = width;

        this.height = height;

    }

    /**
     * @brief overlapRectangles
     * @param r1 矩形1
     * @param r2 矩形2
     * @return boolean 衝突しているかどうか
     * @details 矩形同士の衝突判定を行う
     */
    public boolean overlapRectangles(Rectangle r1, Rectangle r2){

        if(r1.lowerLeft.x < r2.lowerLeft.x + r2.width &&
           r1.lowerLeft.x + r1.width > r2.lowerLeft.x &&
           r1.lowerLeft.y < r2.lowerLeft.y + r2.height &&
           r1.lowerLeft.y + r1.height > r2.lowerLeft.y){

            return true;

        }else{

            return false;

        }

    }

    /**
     * @brief overlapCircleRectangle
     * @param c 円
     * @param r 矩形
     * @return boolean 衝突しているかどうか
     * @details 円と矩形の衝突判定を行う
     */
    public boolean overlapCircleRectangle(Circle c, Rectangle r){

        float closestX = c.center.x;
        float closestY = c.center.y;

        //四角形の上で最も近いポイントを割り出す
        if(c.center.x < r.lowerLeft.x){

            closestX = r.lowerLeft.x;

        }else if(c.center.x > r.lowerLeft.x + r.width){

            closestX = r.lowerLeft.x + r.width;

        }

        if(c.center.y <  r.lowerLeft.y){

            closestY = r.lowerLeft.y;

        }else if(c.center.y > r.lowerLeft.y + r.height){

            closestY = r.lowerLeft.y + r.height;

        }

        //ポイントが円の中にある場合円と四角形は衝突している
        return c.center.distSquared(closestX, closestY) < c.radius * c.radius;

    }
}
