package com.example.towerdefense;

import android.graphics.Path;
import android.graphics.Point;

public class LevelPath
{
    private Path path = new Path();
    private int pathOffset = 0;

    //builds the correct path based on the current level.
    public LevelPath (GameMap.level currentLevel, Point size)
    {
        switch (currentLevel)
        {
            case level1:
                path.moveTo(0, size.y/2 + size.y/15);
                path.lineTo(200, size.y/2 + size.y/15);
                path.cubicTo(size.x/(float)3.02, size.y/(float)1.73, size.x/(float)2.96, size.y/(float)2.84, size.x/(float)2.27, size.y/(float)2.7);
                path.cubicTo(size.x/(float)1.81, size.y/(float)2.45, size.x/(float)1.68, size.y/(float)1.5, size.x/(float)1.38, size.y/(float)1.8);
                path.cubicTo(size.x/(float)1.23, size.y/(float)1.96, size.x/(float)1.14, size.y/2, size.x, size.y/2);

                pathOffset = size.y/54;
                break;

            case level2:
                path.moveTo(0, size.y/2 + size.y/10);
                path.lineTo(size.x/(float)40.32, size.y/2 + size.y/10);
                path.cubicTo(size.x/(float)4.69, size.y/(float)1.56, size.x/(float)4.69, size.y/(float)3.27, size.x/(float)3.25, size.y/(float)3.18);
                path.cubicTo(size.x/(float)2.41, size.y/(float)3.16, size.x/(float)2.6, size.y/(float)1.61, size.x/(float)2.02, size.y/(float)1.41);
                path.cubicTo(size.x/(float)1.61, size.y/(float)1.38, size.x/(float)1.84, size.y/(float)2.14, size.x/(float)1.61, size.y/(float)2.25);
                path.cubicTo(size.x/(float)1.42, size.y/(float)2.4, size.x/(float)1.61, size.y/(float)1.8, size.x/(float)1.11, size.y/(float)1.83);
                path.lineTo(size.x, size.y/(float)1.86);

                pathOffset = (int)(size.y/20.77);
                break;

            case level3:
                path.moveTo(size.x/(float)12.6, size.y + size.y/(float)21.6);
                path.cubicTo(size.x/(float)4.69, size.y/(float)1.3, size.x/(float)87.65, size.y/(float)1.43, size.x/(float)9.42, size.y/(float)2.24);
                path.cubicTo(size.x/(float)3.88, size.y/(float)3.3, size.x/(float)3.99, size.y/(float)1.34, size.x/(float)2.36, size.y/(float)1.51);
                path.cubicTo(size.x/(float)1.86, size.y/(float)1.89, size.x/(float)1.86, size.y/(float)2.82, size.x/(float)1.5, size.y/(float)2.2);
                path.cubicTo(size.x/(float)1.36, size.y/(float)1.75, size.x/(float)1.29, size.y/(float)1.62, size.x/(float)1.2, size.y/(float)1.74);
                path.cubicTo(size.x/(float)1.12, size.y/(float)1.8, size.x/(float)1.04, size.y/(float)1.8, size.x, size.y/(float)1.8);

                pathOffset = (int)(size.y/14.4);
                break;

             default:
                 break;
        }
    }

    //A few getters.
    public Path getPath() { return this.path; }
    public int getPathOffset() { return this.pathOffset; }
}
