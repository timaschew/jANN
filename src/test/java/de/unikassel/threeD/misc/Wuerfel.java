package de.unikassel.threeD.misc;

import java.awt.*;
import java.applet.*;

/*
 * 

Rotation um die x-Achse:

[ 1      0         0    ]   [ x ]   [          x          ]
[ 0   cos(ax)  -sin(ax) ] . [ y ] = [ y*cos(ax)-z*sin(ax) ]
[ 0   sin(ax)   cos(ax) ]   [ z ]   [ y*sin(ax)+z*cos(ax) ]

Rotation um die y-Achse:

[ cos(ay)   0   sin(ay) ]   [ x ]   [ x*cos(ay)+z*sin(ay) ]
[    0      1      0    ] . [ y ] = [          y          ]
[-sin(ay)   0   cos(ay) ]   [ z ]   [-x*sin(ay)+z*cos(ay) ]

Rotation um die z-Achse:

[ cos(az)  -sin(az)  0  ]   [ x ]   [ x*cos(az)-y*sin(az) ]
[ sin(az)   cos(az)  0  ] . [ y ] = [ y*cos(az)+x*sin(az) ]
[    0         0     1  ]   [ z ]   [          z          ]

 * 
 */
public class Wuerfel extends Applet {


	
    // 8 Eckpunkte 1-8
    // mit je 3 Koordinaten 1,2,3
    double p[][] = new double[9][4];

    int x=1, y=2, z=3;

    public void init() {
    	
    	setSize(400, 400);
    	
        setBackground(new Color(255,255,255));

        // 8 Eckpunkte im lokalen Würfel-Koordinatensystem
        // Nullpunkt = Mittelpunkt
        p[1][x] = -100; p[1][y] = -100; p[1][z] = -100;
        p[2][x] = +100; p[2][y] = -100; p[2][z] = -100;
        p[3][x] = +100; p[3][y] = -100; p[3][z] = +100;
        p[4][x] = -100; p[4][y] = -100; p[4][z] = +100;
        p[5][x] = -100; p[5][y] = +100; p[5][z] = -100;
        p[6][x] = +100; p[6][y] = +100; p[6][z] = -100;
        p[7][x] = +100; p[7][y] = +100; p[7][z] = +100;
        p[8][x] = -100; p[8][y] = +100; p[8][z] = +100;

        //       8 - - - - - 7
        //     / |         / |
        //    5 - - - - - 6  |
        //    |  |        |  |
        //    |  4 - - - -|- 3
        //    | /         | /
        //    1 - - - - - 2
    }

    // Rotationswinkel in rad
    double angle_x = 0.01;
    double angle_y = 0.0075;
    double angle_z = 0.005;

    Image buffer;
    Graphics2D gBuffer;

    public void paint(Graphics g) {

        // Double-Buffering
        if (buffer==null) {
            buffer=createImage(this.getSize().width, this.getSize().height);
            gBuffer=(Graphics2D)buffer.getGraphics();
        }
        gBuffer.clearRect(0,0,this.getSize().width, this.getSize().height);

        // Antialiasing
        gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        final int dist = 100;
        // Lokale Würfel-Koordinaten
        // in Welt-Koordinaten: +200
        gBuffer.drawLine((int)(p[1][x])+dist,(int)(p[1][y])+dist,
                         (int)(p[2][x])+dist,(int)(p[2][y])+dist);
        gBuffer.drawLine((int)(p[2][x])+dist,(int)(p[2][y])+dist,
                         (int)(p[3][x])+dist,(int)(p[3][y])+dist);
        gBuffer.drawLine((int)(p[3][x])+dist,(int)(p[3][y])+dist,
                         (int)(p[4][x])+dist,(int)(p[4][y])+dist);
        gBuffer.drawLine((int)(p[4][x])+dist,(int)(p[4][y])+dist,
                         (int)(p[1][x])+dist,(int)(p[1][y])+dist);
        gBuffer.drawLine((int)(p[5][x])+dist,(int)(p[5][y])+dist,
                         (int)(p[6][x])+dist,(int)(p[6][y])+dist);
        gBuffer.drawLine((int)(p[6][x])+dist,(int)(p[6][y])+dist,
                         (int)(p[7][x])+dist,(int)(p[7][y])+dist);
        gBuffer.drawLine((int)(p[7][x])+dist,(int)(p[7][y])+dist,
                         (int)(p[8][x])+dist,(int)(p[8][y])+dist);
        gBuffer.drawLine((int)(p[8][x])+dist,(int)(p[8][y])+dist,
                         (int)(p[5][x])+dist,(int)(p[5][y])+dist);
        gBuffer.drawLine((int)(p[1][x])+dist,(int)(p[1][y])+dist,
                         (int)(p[5][x])+dist,(int)(p[5][y])+dist);
        gBuffer.drawLine((int)(p[2][x])+dist,(int)(p[2][y])+dist,
                         (int)(p[6][x])+dist,(int)(p[6][y])+dist);
        gBuffer.drawLine((int)(p[3][x])+dist,(int)(p[3][y])+dist,
                         (int)(p[7][x])+dist,(int)(p[7][y])+dist);
        gBuffer.drawLine((int)(p[4][x])+dist,(int)(p[4][y])+dist,
                         (int)(p[8][x])+dist,(int)(p[8][y])+dist);

        g.drawImage (buffer, 0, 0, this);

        // Verzögerung
        try {Thread.sleep(10);}
        catch (InterruptedException e) {}

        double px, py, pz;

        for (int i=1;i<9;i++) {

            py = p[i][y];
            pz = p[i][z];

            // Rotation um x-Achse
            //p[i][x] = px;
            p[i][y] = py*Math.cos(angle_x)-pz*Math.sin(angle_x);
            p[i][z] = py*Math.sin(angle_x)+pz*Math.cos(angle_x);


            px = p[i][x];
            pz = p[i][z];
            
            
            // Rotation um y-Achse
            p[i][x] = px*Math.cos(angle_y)+pz*Math.sin(angle_y);
            //p[i][y]= py;
            p[i][z] =-px*Math.sin(angle_y)+pz*Math.cos(angle_y);

            py = p[i][y];
            px = p[i][x];


            // Rotation um z-Achse
            p[i][x] = px*Math.cos(angle_z)-py*Math.sin(angle_z);
            p[i][y] = py*Math.cos(angle_z)+px*Math.sin(angle_z);
          //p[i][z]= pz;
        }

        repaint();
    }

    public void update(Graphics g) {paint(g);}
}
