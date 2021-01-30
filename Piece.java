import java.awt.Color;

/**
 * Una pieza que representa una de las posibles piezas del
 * Tetris o tetriminos, formada por objetos Block los cuales se almacenan
 * en una matriz de dos dimensiones de tamaño suficiente (3x3 o 4x4) para contener
 * cada una de las 7 posibilidades de piezas existentes en el Tetris.
 * 
 * @author Octavio Martínez 
 * @version 16.05.2011
 */
public class Piece
{
    // El tamaño de la matriz que contiene a los tetriminos
    private int piece_rows, piece_cols;
    // La matriz que contiene todas las formas posibles de los tetriminos
    private Block[][] piece;
    // Las posiciones de la pieza en los giros según tipos de tetriminos
    private int piecePositions;
    
    /**
     * Constructor con 7 opciones que se invocarán mediante un random 0-6
     * para construir 7 posibles configuraciones de pieza que representan
     * cada uno de los 7 posibles tetriminos
     * @param selector El número que especifica que inicialización se hará
     */
    public Piece(int selector)
    {
        // establece el tamaño por defecto de la matriz para el caso general(3x3)
        piece_rows = 3; piece_cols = 3;
        // realiza una de 7 configuraciones de bloques en la matriz
        // que representan uno de los 7 posibles tetriminos
        switch(selector) {
            // construimos el palo. Establecemos caso especial de 4 rows 4 cols (para giros)
            case 0: piece_rows = 4; piece_cols = 4;
                piece = new Block[piece_rows][piece_cols]; makeStick(); piecePositions = 2; break;
            // construimos el cubo
            case 1: piece = new Block[piece_rows][piece_cols]; makeCube(); piecePositions = 1; break;    
            // construimos la T    
            case 2: piece = new Block[piece_rows][piece_cols]; makeT();  piecePositions = 4; break;   
            // construimos la L izq   
            case 3: piece = new Block[piece_rows][piece_cols]; makeLeftL();  piecePositions = 4; break;  
            // construimos la L der   
            case 4: piece = new Block[piece_rows][piece_cols]; makeRightL(); piecePositions = 4; break; 
            // construimos la S izq    
            case 5: piece = new Block[piece_rows][piece_cols]; makeLeftS(); piecePositions = 4; break; 
            // construimos la S der    
            case 6: piece = new Block[piece_rows][piece_cols]; makeRightS(); piecePositions = 4; break;
            // Resto de casos no se hace nada. No puede ocurrir, usaremos rand(7) como máximo.    
            default: ;
        }
    }
    
    /**
     * Acceso a las posiciones posibles de la pieza para realizar los giros
     * @return El número de posiciones relevantes de la pieza en un giro
     */
    public int getPositions()
    {
        return piecePositions;
    }    

    /**
     * Devuelve el objeto almacenado en una de las celdas de la matriz
     * @param row La coordenada fila de la celda
     * @param col La coordenada columna de la celda
     * @return El objeto bloque almacenado en esa celda, o null si no hay ninguno
     */
    public Block getBlock(int row, int col)
    {
        return piece[row][col];
    }
    
    /**
     * Añade un bloque en una posición específica de la matriz de la pieza
     * @param p_row La fila donde vamos a añadir el bloque
     * @param p_col La columna donde vamos a añadir el bloque
     * @param color El color del bloque que vamos a añadir
     */
    private void addOneBlock(int p_row, int p_col, Color color)
    {
        piece[p_row][p_col] = new Block(color, p_row, p_col);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "palo"
     */
    private void makeStick()
    {
        addOneBlock(1,0, Color.red);
        addOneBlock(1,1, Color.red);
        addOneBlock(1,2, Color.red);
        addOneBlock(1,3, Color.red);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "cubo"
     */
    private void makeCube()
    {
        addOneBlock(0,1,Color.blue);
        addOneBlock(0,2,Color.blue);
        addOneBlock(1,1,Color.blue);
        addOneBlock(1,2,Color.blue);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "T"
     */
    private void makeT()
    {
        addOneBlock(0,1,Color.yellow);
        addOneBlock(1,0,Color.yellow);
        addOneBlock(1,1,Color.yellow);
        addOneBlock(1,2,Color.yellow);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "L izquierda"
     */
    private void makeLeftL()
    {
        addOneBlock(0,0,Color.green);
        addOneBlock(1,0,Color.green);
        addOneBlock(1,1,Color.green);
        addOneBlock(1,2,Color.green);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "L derecha"
     */
    private void makeRightL()
    {
        addOneBlock(0,2,Color.magenta);
        addOneBlock(1,0,Color.magenta);
        addOneBlock(1,1,Color.magenta);
        addOneBlock(1,2,Color.magenta);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "S izquierda"
     */
    private void makeLeftS()
    {
        addOneBlock(0,0,Color.cyan);
        addOneBlock(0,1,Color.cyan);
        addOneBlock(1,1,Color.cyan);
        addOneBlock(1,2,Color.cyan);
    }
    
    /**
     * Crea la configuración de la matriz para representar el tetrimino "S derecha"
     */
    private void makeRightS()
    {
        addOneBlock(0,1,Color.orange);
        addOneBlock(0,2,Color.orange);
        addOneBlock(1,0,Color.orange);
        addOneBlock(1,1,Color.orange);
    }
    
    
    /**
     * Acceso a la altura de la matriz de la pieza
     * @return El número de filas que tiene la matriz de la pieza
     */
    public int getPieceRows()
    {
        return piece_rows;
    }
    
    /**
     * Acceso a la anchura de la matriz de la pieza
     * @return El número de columnas que tiene la matriz de la pieza
     */
    public int getPieceCols()
    {
        return piece_cols;
    }
        
}
