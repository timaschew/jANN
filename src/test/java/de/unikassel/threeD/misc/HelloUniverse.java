package de.unikassel.threeD.misc;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class HelloUniverse extends Applet {

	public BranchGroup createSceneGraph() {
		// wihout mouse
		
		// BranchGroup objRoot = new BranchGroup();
		// TransformGroup objTrans = new TransformGroup();
		// objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		// objRoot.addChild( objTrans );
		// objTrans.addChild( new ColorCube( 1.0 ));
		// Transform3D yAxis = new Transform3D();
		// Alpha rotationAlpha = new Alpha( -1, 4000 );
		// RotationInterpolator rotator= new RotationInterpolator(rotationAlpha,
		// objTrans, yAxis, 0.0f, (float) Math.PI*2.0f);
		// BoundingSphere bounds = new BoundingSphere( new Point3d( 0.0,0.0,0.0
		// ), 100.0 );
		// rotator.setSchedulingBounds( bounds );
		// objRoot.addChild( rotator );
		// objRoot.compile();
		// return objRoot;
		
		/*
		 * root
		 * 	- objTrans
		 * 		- zTrans
		 * 			- vector (0,0,-5)
		 * 		- prim (Box)
		 * 		- mouse behavior
		 *  - light
		 *  	- color (1,1,1)
		 *  	- vector (0,0,-1)
		 */
		
		BranchGroup root = new BranchGroup();
		Transform3D zTrans = new Transform3D();
		
		Vector3f vector = new Vector3f(0.0f, 0.0f, -5.0f);
		zTrans.set(vector);
		TransformGroup objTrans = new TransformGroup(zTrans);
		root.addChild(objTrans);
		// Box erzeugen und einhängen
		Box prim = new Box();
		objTrans.addChild(prim);
		// BoundingSpere für Mousebehavior und Lichtquelle erzeugen
		Point3d center = new Point3d(0.0, 0.0, 0.0);
		double radius = 100.0;
		BoundingSphere bounds = new BoundingSphere(center, radius);
		// Mouse-Rotation-Behavior ersetzen und in Transformgruppe einhängen +
		// Capabilitybits setzen
		MouseRotate behavior = new MouseRotate(objTrans);
		objTrans.addChild(behavior);
		behavior.setSchedulingBounds(bounds);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		// Lichtquelle erzeugen und in Szenengraphen hängen
		Color3f lightColor = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f lightDirection = new Vector3f(-3f, -2f, -1.0f);
		DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
		light.setInfluencingBounds(bounds);
		root.addChild(light);
		// Content-Branch optimieren und zurückgeben
		root.compile();
		return root;

	}

	public HelloUniverse() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D c = new Canvas3D(config);
		add("Center", c);
		SimpleUniverse u = new SimpleUniverse(c);
		u.getViewingPlatform().setNominalViewingTransform();
		BranchGroup scene = createSceneGraph();
		u.addBranchGraph(scene);
	}

	public static void main(String[] args) {
		new MainFrame(new HelloUniverse(), 600, 600);
	}
}
