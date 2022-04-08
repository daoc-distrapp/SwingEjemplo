package swing_ej;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	JLabel label = new JLabel("Hola Mundo !!!");;
	
    public static void main(String[] args) {
    	final Main frame = new Main();
        //Agenda la ejecución de esta tarea en la event-dispatching thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.createAndShowGUI();
            }
        });
    }
	
	/**
     * Presenta la GUI
     * Debe ejecutarse en la EDT
     */
    private void createAndShowGUI() {
        this.setTitle("Hola Mundo Swing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel(new GridLayout(0, 1));
        this.add(content, BorderLayout.CENTER);
 
        //Añade la etiqueta
        content.add(label);
 
        //Añade botones
        JButton buttonR = new JButton("Rápido");
        buttonR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText("Acción rápida");
			}
		});
        content.add(buttonR);
        
        JButton buttonL = new JButton("Lento Worker");
        buttonL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText("Espere...");
				new MyWorker().execute();
			}
		});
        content.add(buttonL);        
        
        JButton buttonT = new JButton("Lento Thread");
        buttonT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText("Espere...");
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(3000);
					        SwingUtilities.invokeLater(new Runnable() {
					            public void run() {
					            	label.setText("Acción por Thread");
					            }
					        });
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
        content.add(buttonT);          
        
        //Presenta la ventana
        this.pack();
        this.setVisible(true);
    }

    /*
     * Nueva thread Swing, para acciones largas.
     * Evita que se atore la GUI y deje de responder
     */
    private class MyWorker extends SwingWorker<String, Void> {
		@Override
		protected String doInBackground() throws Exception {
			Thread.sleep(3000);
			return "Acción por Worker!!!";
		}
		@Override
		protected void done() {
			try {
				label.setText(get());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}  	
    }
    
}
