import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa una vista gr�fica de la pantalla de juego, como una matriz
 * de 25 filas por 12 columnas, en la cual cada celda puede almacenar un objeto.
 * Cada posici�n de la matriz se representa como un rect�ngulo con un color
 * definido, en funci�n de si est�n vac�as (null) o no.
 * Se puede establecer un color para cada tipo de objeto mediante setColor.
 * 
 * @author Octavio Mart�nez
 * @version 16.05.2011
 */
public class GameView extends JFrame
{
    // Usamos el color negro por defecto para posiciones vac�as
    private final Color EMPTY_COLOR = Color.black;
    // instancia de la clase interna que proporciona la forma de pintar la matriz
    private GridView gridView;
    // elementos del menu del juego
    private JMenuItem start, stop, resume, reset, quit;

    /**
     * Construye una vista gr�fica de la pantalla de juego
     * @param height La altura de la matriz de juego.
     * @param width  La anchura de la matriz de juego.
     */
    public GameView(int height, int width)
    {
        setTitle("Tetris");
        setLocation(800, 50);
        
        gridView = new GridView(height, width);
        
        Container contents = getContentPane();
        contents.add(gridView, BorderLayout.CENTER);
        
        makeMenuBar();
        
        pack();
        setVisible(true);
    }
    
    /**
     * Crea una barra de men� con opciones b�sicas del juego
     */
    private void makeMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu game = new JMenu("Game");
        menuBar.add(game);
        
        start = new JMenuItem("Start");
        game.add(start);
        
        stop = new JMenuItem("Pause");
        game.add(stop);
        
        resume = new JMenuItem("Resume");
        game.add(resume);
        
        reset = new JMenuItem("Reset");
        game.add(reset);
        
        quit = new JMenuItem("Quit");
        game.add(quit);
    }
    
    /**
     * Acceso al elemento de men� Start
     */
    public JMenuItem getStart()
    {
        return start;
    }
    
    /**
     * Acceso al elemento de men� Stop
     */
    public JMenuItem getStop()
    {
        return stop;
    }
    
    /**
     * Acceso al elemento de men� Resume
     */
    public JMenuItem getResume()
    {
        return resume;
    }
    
    /**
     * Acceso al elemento de men� Reset
     */
    public JMenuItem getReset()
    {
        return reset;
    }
    
    /**
     * Acceso al elemento de men� Quit
     */
    public JMenuItem getQuit()
    {
        return quit;
    }

    /**
     * Muestra en pantalla el estado actual de la matriz que representa
     * la pantalla de juego diferenciando por colores entre celdas
     * vac�as y ocupadas por bloques
     * @param grid La matriz sobre la cual mostramos su estado actual.
     */
    public void showStatus(Grid grid)
    {
        // hacemos visible el frame si a�n no lo es
        if(!isVisible())
            setVisible(true);
            
        gridView.preparePaint();

        for(int row = 0; row < grid.getHeight(); row++) {
            for(int col = 0; col < grid.getWidth(); col++) {
                // si hay un bloque en esa celda lo pintamos de su color y en 3D
                if(grid.getObjectAt(row, col) != null) {
                    gridView.drawMark3D(col, row, grid.getCellColor(row, col));
                // si no hay bloque pintamos negro y en 2D    
                } else {
                    gridView.drawMark2D(col, row, EMPTY_COLOR);
                }
            }
        }
        // llamamos impl�citamente a nuestro paintComponent desde aqu� para pintar el estado
        gridView.repaint();
    }
    
    /**
     * Clase interna de GameView que proporciona una forma de mostrar
     * en pantalla la matriz de juego rectangular, pintando rect�ngulos
     * de igual tama�o para cada celda de la matriz (posici�n: row-col)
     * y diferenciando por medio de colores las celdas vac�as
     * (en negro) de las que est�n ocupadas por bloques de las piezas del
     * tetris (en otros colores).
     */
    private class GridView extends JPanel
    {
        // la escala de los rect�ngulos que vamos a pintar
        private final int GRID_VIEW_SCALING_FACTOR = 15;
        
        // la anchura y altura de la matriz que vamos a pintar
        private int gridWidth, gridHeight;
        // las proporciones horizontales y verticales de los rect�ngulos
        private int xScale, yScale;
        // las dimensiones 2D del rect�ngulo de juego
        Dimension size;
        // el contexto gr�fico necesario para pintar en los componentes
        private Graphics g;
        // la imagen que vamos a pintar
        private Image gridImage;

        /**
         * Construye un nuevo componente interno de la matriz dibujable.
         * @param height La altura de la matriz
         * @param width La anchura de la matriz
         */
        public GridView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Dice al gestor de la interfaz gr�fica el tama�o del �rea del juego
         * @return El �rea total en 2D del rect�ngulo de juego.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Inicializa las variables necesarias para poder pintar
         * la matriz y tiene en cuenta si hemos modificado el tama�o
         * para escalarlo adecuadamente.
         */
        public void preparePaint()
        {
            // si el tama�o ha cambiado
            if(!size.equals(getSize())) {
                size = getSize();
                gridImage = gridView.createImage(size.width, size.height);
                g = gridImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Pinta en 3D y de un determinado color cada rect�ngulo que representa
         * una posici�n de la matriz
         * @param x La coordenada x de la posici�n
         * @param y La coordenada y de la posici�n
         * @param color El color designado para esa celda.
         */
        public void drawMark3D(int x, int y, Color color)
        {
            g.setColor(color);
            g.fill3DRect(x * xScale, y * yScale, xScale-1, yScale-1, true);
        }
        
        /**
         * Pinta en 2D de un determinado color cada rect�ngulo que representa
         * una posici�n de la matriz
         * @param x La coordenada x de la posici�n
         * @param y La coordenada y de la posici�n
         * @param color El color designado para esa celda.
         */
        public void drawMark2D(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * Copia el componente que representa la imagen interna 
         * dibujable de la matriz a la pantalla
         * para ser mostrado y le pinta.
         * @param g El objeto de contexto gr�fico que permite dibujar en los componentes
         */
        public void paintComponent(Graphics g)
        {
            if(gridImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(gridImage, 0, 0, null);
                // Si el tama�o del componente ha variado se pinta reescalado.    
                } else {
                    g.drawImage(gridImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
