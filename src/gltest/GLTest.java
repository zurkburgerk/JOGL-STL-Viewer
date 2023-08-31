package gltest;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.GLEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class GLTest implements GLEventListener {
	
	private final JFrame frame;
	private final GLCanvas canvas;
	private LinkedList<Float> vertices;
	
	public GLTest(String title) {
		
		vertices = scanFile("C:\\Users\\jrathum\\eclipse-workspace\\gltest\\src\\Iron_Throne_Benchy.stl");
		scaleVertices(1);
		
        GLProfile gp = GLProfile.get(GLProfile.GL2);
        GLCapabilities cap = new GLCapabilities(gp);
        
        canvas = new GLCanvas(cap);
        canvas.addGLEventListener(this);
        canvas.setSize(350, 350);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(500, 500);
        frame.setVisible(true);
        
        final FPSAnimator animator = new FPSAnimator(canvas, 300, true); 
        animator.start(); 
        
	}
	
	/**
	 * Scans an STL file for all vertex values and places them in a linked list.
	 * @param path path to the STL file.
	 * @return LinkedList<Float> linked list of float values in groupings of 3 for 3d vertex values.
	 */
	private LinkedList<Float> scanFile(String path) {
		LinkedList<Float> list = new LinkedList<Float>();
		File file = new File(path);
	    
	    try {
	    	Scanner sc = new Scanner(file);
	    	while (sc.hasNextLine()) {
	    		String line = sc.nextLine().trim();
	    		if (line.startsWith("solid model")) continue;
	    		if (line.startsWith("end loop")) continue;
	    		if (line.startsWith("outer loop")) {
	    			for (int i = 0; i < 3; ++i) {
	    				line = sc.nextLine().trim();
		    			String[] split = line.split(" ");
		    			float x = Float.parseFloat(split[1]);
		    			float y = Float.parseFloat(split[2]);
		    			float z = Float.parseFloat(split[3]);
		    			list.add(x);
		    			list.add(y);
		    			list.add(z);
	    			}
	    		}
	    	}
	    	sc.close();
	    }
	    catch(FileNotFoundException e) {
	    	e.printStackTrace();
	    }
	    System.out.println("Scanned " + list.size()/3 + " triangles.");
	    return list;
	}
	
	/**
	 * Scales all float values scanned from stl file to a number between -1 and 1.
	 * @param scale number used to scale. 1 is exact size, less than 1 is bigger, greater than 1 is smaller.
	 */
	private void scaleVertices(float scale) {
		Iterator<Float> it = vertices.iterator();
		float max = 0;
		while (it.hasNext()) {
			float num = Math.abs(it.next());
			if (num > max) max = num;
		}
		
		LinkedList<Float> newlist = new LinkedList<Float>();
		it = vertices.iterator();
		while (it.hasNext()) {
			newlist.add(it.next()/(scale*max));
		}
		vertices = newlist;
	}
	
	float angle = 0;
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear (GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT );  
	      
	    // Clear The Screen And The Depth Buffer 
	    gl.glLoadIdentity();  // Reset The View     
	              
	    //triangle rotation      
	    gl.glRotatef( angle, 1.0f, 1.0f, 1.0f );  
	    
	    gl.glBegin(GL2.GL_TRIANGLES);
    	Iterator<Float> it = vertices.iterator();
	    while(it.hasNext()) {
		    float x = it.next();
		    float y = it.next();
		    float z = it.next();
		    gl.glVertex3f(x, y, z);
	    }
	    gl.glEnd();
	    gl.glFlush(); 
	      
	    angle += 2f;  //assigning the angle  
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
}
