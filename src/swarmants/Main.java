/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmants;

import processing.core.*;
import traer.physics.*;
import java.lang.Math;

/**
 *
 * @author Frankenteddy
 */
public class Main extends PApplet {

    Particle mouse;
    int numberOfAnts = 800;
    int widthOfAnts = 5;
    Particle[] ants = new Particle[numberOfAnts];
    ParticleSystem physics;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PApplet.main( new String[]{"swarmants.Main"} );
    }

    @Override
    public void setup() {

        size(800, 800);
        noStroke();
        noCursor();
        smooth();

        physics = new ParticleSystem(0.0f, 0.0f, 0.0f, 0.0f);
        mouse = physics.makeParticle();
        mouse.makeFixed();

        int xPos = 100;
        int yPos = 300;

        for(int i=0; i<numberOfAnts; i++) {

            if(xPos >= width-100) {
                xPos = 100;
                yPos+=10;
            } else {
                xPos+=10;
            }

            ants[i] = physics.makeParticle(1.0f, xPos, yPos, 0);
            physics.makeAttraction(mouse, ants[i], 50000.0f, 100);

            if(i > 0) {
                if(i%2 == 0) {
                    physics.makeAttraction(ants[i-1], ants[i], -100.0f, 10);
                } else {
                    physics.makeAttraction(ants[i-1], ants[i], 100.0f, 10);
                }
            }

        }

    }

    @Override
    public void draw() {

        mouse.position().set(mouseX, mouseY, 0);

        for(int i=0; i<10; i++) {
            handleBoundaryCollisions(ants[i]);
        }

        physics.tick();

        fill(color(255, 255, 255, 255));
        ellipse(mouse.position().x(), mouse.position().y(), 35, 25);

        for(int i=0; i<numberOfAnts; i++) {

            if(i > 0) {
                if(i%2 == 0) {
                    fill(color(0, 255, 0, 255));
                } else {
                    fill(color(255, 0, 0, 255));
                }
            } else {
                fill(color(0, 255, 0, 255));
            }

            ellipse(ants[i].position().x(), ants[i].position().y(), widthOfAnts, widthOfAnts);
        }

        fill(color(0,0,0,64));
        rect(0, 0, width, height);

    }

    void handleBoundaryCollisions( Particle p ) {
        if ( p.position().x() < 0 || p.position().x() > width ) {
            p.velocity().set(-0.9f*p.velocity().x(), p.velocity().y(), 0);
        }
        if ( p.position().y() < 0 || p.position().y() > height ) {
            p.velocity().set(p.velocity().x(), -0.9f*p.velocity().y(), 0);
        }
        p.position().set(constrain( p.position().x(), 0, width ), constrain( p.position().y(), 0, height ), 0);
    }

}
