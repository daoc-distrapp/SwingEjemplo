package swing_ej;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Main {
	static final JLabel label = new JLabel("Hola Mundo !!!");;
	
    public static void main(String[] args) {
        //Agenda la ejecuci�n de esta tarea en la event-dispatching thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	/**
     * Presenta la GUI
     * Debe ejecutarse en la EDT
     */
    private static void createAndShowGUI() {
        //Crea y setea la ventana
        JFrame frame = new JFrame("Hola Mundo Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        frame.setContentPane(contentPane);
 
        //A�ade la etiqueta
        frame.add(label);
 
        //A�ade botones
        JButton buttonR = new JButton("R�pido");
        buttonR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText("Acci�n r�pida");
			}
		});
        frame.add(buttonR);
        
        JButton buttonL = new JButton("Lento");
        buttonL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.execute();
			}
		});
        frame.add(buttonL);        
        
        JButton buttonT = new JButton("Thread");
        buttonT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(3000);
					        SwingUtilities.invokeLater(new Runnable() {
					            public void run() {
					            	label.setText("Acci�n por Thread");
					            }
					        });
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
        frame.add(buttonT);          
        
        //Presenta la ventana
        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Nueva thread Swing, para acciones largas.
     * Evita que se atore la GUI y deje de responder
     */
    static SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
		@Override
		protected String doInBackground() throws Exception {
			Thread.sleep(3000);
			return "Acci�n lenta !!!";
		}
		
		protected void done() {
			try {
				label.setText(get());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		};
    };
    
}
