import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.*;
import javax.swing.*;

/**
 * Una simulación del juego Tetris con las funcionalidades
 * básicas del juego original
 * 
 * @author Octavio Martínez
 * @version 17.05.2011
 */
public class Tetris
{
    // Retraso preestablecido para el temporizador que controla la caida de las piezas.
    private static final int TIME_DELAY = 1000;
    // la matriz 2D del juego
    private Grid grid;
    // La interfaz gráfica de la matriz del juego
    private GameView gameView;
    // Un temporizador para controlar la caida automática de las piezas
    private Timer timer;
    
    /**
     * Crea un tetris con valores de matriz por defecto 25x12
     */
    public Tetris()
    {
        // crea una matriz y una vista gráfica de la misma de dimensiones por defecto
        grid = new Grid(Grid.DEFAULT_ROWS, Grid.DEFAULT_COLS);
        gameView = new GameView(Grid.DEFAULT_ROWS, Grid.DEFAULT_COLS);
        // añade el oyente de eventos por teclado para controlar las piezas
        gameView.addKeyListener(new ArrowListener());
        
        // comienza el juego con el elemento del menu "Start"
        gameView.getStart().addActionListener(new ActionListener() 
            {public void actionPerformed(ActionEvent e) {startGame();} });
        
        // para el juego con el elemento del menu "Pause"
        gameView.getStop().addActionListener(new ActionListener() 
            {public void actionPerformed(ActionEvent e) {stopTimer();} });
            
        // continua el juego con el elemento del menu "Resume"
        gameView.getResume().addActionListener(new ActionListener() 
            {public void actionPerformed(ActionEvent e) {startGame();} });
            
        // resetea el juego con el elemento del menu "Reset"
        gameView.getReset().addActionListener(new ActionListener() 
            {public void actionPerformed(ActionEvent e) {reset();} });
            
        // sale del juego con el elemento del menu "Quit"
        gameView.getQuit().addActionListener(new ActionListener() 
            {public void actionPerformed(ActionEvent e) {System.exit(0);} });    
            
        // pinta la pantalla de juego
        gameView.showStatus(grid);
        
        // crea una nueva pieza en su ubicación inicial en la matriz
        newTetrimino();
        // comienza la ejecución del juego por medio del temporizador
        startGame();
    }
        
    /**
     * Reestablece el juego a un estado inicial, vaciando la matriz de bloques
     */
    private void reset()
    {
        grid.clear();
        gameView.showStatus(grid);
    }
    
    /**
     * Aparece la nueva pieza
     */
    private void newTetrimino()
    {
        grid.createTetrimino();
        gameView.showStatus(grid);
    }
    
    /**
     * Implementa el movimiento descendente de las piezas en la
     * matriz de juego mediante un temporizador fijado con
     * un retraso de 1000 milisegundos.
     */
    private void startGame()
    {
        // crea el objeto temporizador
        timer = new Timer(TIME_DELAY, new FallActionListener());
        // inicia el proceso de temporización
        timer.start();   
    }
    
    /**
     * Detiene el temporizador.
     */
    private void stopTimer()
    {
        timer.stop();
    }    
    
    /**
     * Moviemiento descendente de toda la pieza conjuntamente
     * en el Grid.
     */
    private void fall()
    {
        grid.moveDown();
        gameView.showStatus(grid);
    }
    
    /**
     * Mueve a la derecha una pieza en el grid si es posible dicho movimiento
     */
    private void moveRight()
    {
        grid.moveRight(); 
        gameView.showStatus(grid);
    }
    
    /**
     * Mueve a la izquierda una pieza en el grid si es posible dicho movimiento
     */
    private void moveLeft()
    {
        grid.moveLeft();  
        gameView.showStatus(grid);
    }
    
    /**
     * Intenta girar 90º una pieza en la matriz si dicho giro es válido
     */
    private void turnTetrimino()
    {
        grid.turnPiece();
        gameView.showStatus(grid);
    }
     
    
    /**
     * Clase interna que servirá de oyente de eventos para
     * la caída de los bloques. En cada paso del timer realiza su acción,
     * que consiste en bajar una fila la pieza, poner a 0 el contador de giros
     * y hacer "linea" si se dan las condiciones
     */
    public class FallActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            fall();
            // reset del contador de giros para permitir 4 por segundo
            grid.setTurns(0);
            grid.searchLines();
            // si toca fondo la pieza se crea una nueva
            if(grid.allStacked()) {
                newTetrimino();
            }
            // si las piezas llegan arriba de la matriz paramos el timer y se acaba el juego
            for(int col = grid.DEFAULT_COLS-1; col >= 0; col--) {
                if(grid.getObjectAt(0,col) != null && grid.getObjectAt(0,col).isStacked()) {
                    stopTimer();
                    JOptionPane gameOver = new JOptionPane();
                    gameOver.showMessageDialog(gameView, "GAME OVER!!", "Game Over", gameOver.INFORMATION_MESSAGE);
                    
                }    
            }
        }   
        
          
    }
    
    /**
     * Clase interna que implementa KeyListener usada para realizar las acciones
     * de mover y girar al presionar las teclas de dirección apropiadas
     */
    public class ArrowListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            if(e.getKeyCode() == 39) {
                moveRight();
            } else if (e.getKeyCode() == 37) {
                moveLeft();
            } else if (e.getKeyCode() == 38) {
                turnTetrimino();
            } else if (e.getKeyCode() == 40) {
                fall();
            }    
        }
        
        public void keyReleased(KeyEvent e2)
        {
            
        }
        
        public void keyTyped(KeyEvent e3)
        {
            
        }    
    }
    
    /**
     * Rutina main para ejecutar el programa fuera de BlueJ
     */
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
    }    
        
}
