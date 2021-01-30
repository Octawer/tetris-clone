import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Representa una matriz bidimensional de celdas en cada
 * una de las cuales se puede almacenar una de los 4 bloques
 * que forman cada tetrimino.
 * 
 * @author Octavio Martínez
 * @version 16.05.2011
 */
public class Grid
{
    // variable aleatoria usada para la creación de los tetriminos
    private static final Random rand = new Random();
    // la anchura (columnas) por defecto de la matriz
    public static final int DEFAULT_COLS = 12;
    // la altura (filas) por defecto de la matriz.
    public static final int DEFAULT_ROWS = 25;
    // controla el numero de giros de la pieza
    private int turns;
    
    // Las dimensiones 2D de la matriz, filas y columnas que posee.
    private int rows, cols;
    // El Array bidimensional que almacenará los bloques de las piezas o tetriminos.
    private Block[][] grid;
    // La pieza actual
    private Piece tetrimino;
    // mantiene la noción en el grid de bloque central del tetrimino actual.
    private Block central;
    

    /**
     * Construye una matriz de las dimensiones especificadas
     * @param rows Las filas de la matriz
     * @param cols Las columnas de la matriz
     */
    public Grid(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        grid = new Block[rows][cols];
        tetrimino = null;
        central = null;
    }
    
    /**
     * Vacía el grid poniendo a null todas sus posiciones
     */
    public void clear()
    {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                grid[row][col] = null;
            }
        }
    }  
    
    /**
     * Pone un bloque de la pieza en una posición
     * dada de la matriz y establece sus coords internas para que coincidan
     * con la posición real dentro del grid
     * @param element El bloque que queremos poner
     * @param row La fila en que queremos ubicarle
     * @param col La columna en que queremos ubicarle
     */
    private void placePiece(Block element, int row, int col)
    {
        if(element != null) {
            // establecemos los atributos del bloque para que coincidan con
            // su posición real en el grid
            element.setLocation(row,col);
            grid[row][col] = element;
        }    
    }  
    
    /**
     * Devuelve el objeto en la celda especificada (fila-columna)
     * @param row La fila.
     * @param col La columna.
     * @return el objeto en la posición dada, o null si no hay ninguno
     */
    public Block getObjectAt(int row, int col)
    {
        if(grid[row][col] != null) { 
            return grid[row][col];
        } else {
            return null;
        }    
    }
    
    /**
     * Devuelve el color de un determinado bloque en la matriz
     * @param row La fila donde vamos a buscar
     * @param col La columna donde vamos a buscar.
     * @return El color de un bloque presente en la matriz, o null si no hay bloque
     */
    public Color getCellColor(int row, int col)
    {
        Color color = getObjectAt(row, col).getBlockColor();
        return color;
    }    
    
    /**
     * Vacía una celda concreta de la matriz estableciendo a null su referencia
     * @param row La fila de la matriz
     * @param col la columna de la matriz
     */
    private void eraseCell(int row, int col)
    {
        grid[row][col] = null;    
    }
    
    /**
     * Obtiene un bloque de una ubicación del grid y lo elimina de esa
     * posición
     * @param row La fila en la que está el bloque
     * @param col La columna en la que está el bloque
     * @return El bloque que estaba en esa posición si había alguno, null de otro modo
     */
    private Block cutBlock(int row, int col)
    {
        if(grid[row][col] != null) {
            Block block = grid[row][col];
            grid[row][col] = null;
            return block;
        } else {
            return null;
        }
    }    
    
    /**
     * Establece todas los bloques de las piezas del grid
     * como "apilados" para tenerlo en cuenta y no moverlas 
     * al recorrer la matriz en otros métodos de modificación
     */
    private void setStacked()
    {
         for(int row = DEFAULT_ROWS - 1; row >= 0; row--) {
            for(int col = DEFAULT_COLS - 1; col >= 0; col--) {
                Block block = getObjectAt(row,col);
                if(block != null) {
                    block.setStacked();
                }    
            }
        }   
    }
    
    /**
     * Comprueba si todas los bloques de las piezas en el grid están apilados
     * @return True si estan todos apilados, false en caso contrario
     */
    public boolean allStacked()
    {
        boolean check = true;
        for(int row = DEFAULT_ROWS - 1; row >= 0; row--) {
            for(int col = DEFAULT_COLS - 1; col >= 0; col--) {
                Block block = getObjectAt(row,col);
                if(block != null && !block.isStacked()) {
                    check = false;
                }
            }
        }
        return check;
    }   
                
    
    /**
     * Crea un nuevo tetrimino en la posición inicial del grid,
     * es decir, en la zona superior central
     */
    public void createTetrimino()
    {
        turns = 0;
        int dice7 = rand.nextInt(7);
        tetrimino = new Piece(dice7);
        // marca en bloque central de la pieza para seguirle en el grid.
        central = tetrimino.getBlock(1,1);
        // colocar en el grid un bloque si !null en la submatriz Piece
        for( int pieceRow = 0; pieceRow < tetrimino.getPieceRows(); pieceRow++) {
            for( int pieceCol = 0; pieceCol < tetrimino.getPieceCols(); pieceCol++) {
                if(tetrimino.getBlock(pieceRow, pieceCol) != null) {
                    placePiece(tetrimino.getBlock(pieceRow, pieceCol), pieceRow, pieceCol + 4);
                }
            }
        }
    }
    
    /**
     * Acceso al campo tetrimino de esta clase
     * @return El campo tetrimino de una instancia de esta clase
     */
    private Piece getTetrimino()
    {
        return tetrimino;
    }     
    
    /**
     * Devuelve la altura (filas) de la matriz.
     * @return La altura de la matriz.
     */
    public int getHeight()
    {
        return rows;
    }
    
    /**
     * Devuelve el ancho (cols) de la matriz
     * @return El ancho de la matriz.
     */
    public int getWidth()
    {
        return cols;
    }
    
    /**
     * Devuelve el numero de giros de la pieza
     * @return El número de giros efectuados por una pieza dentro del grid
     */
    private int getTurns()
    {
        return turns;
    }
    
    /**
     * Pone el contador de giros a un valor determinado
     * @param value El valor al que queremos establecer el campo turns
     */
    public void setTurns(int value)
    {
        turns = value;
    }    
    
    /**
     * Devuelve un array integrado
     * por los 4 bloques de la pieza móvil actual
     * @return Una lista de los 4 bloques pertenecientes a la pieza móvil actual
     */
    private ArrayList<Block> getBlocksAtGrid()
    {
        ArrayList<Block> blocksAtGrid = new ArrayList<Block>();
        for(int row = 0; row <= DEFAULT_ROWS-1; row++){
            for(int col = 0; col <= DEFAULT_COLS-1; col++) {
                Block block = getObjectAt(row,col);
                // si existe bloque en esa posición y no está apilado
                if(block != null && !block.isStacked()) {
                    blocksAtGrid.add(block);
                }
            }
        }   
        return blocksAtGrid;
    }
    
    /**
     * Mueve toda la pieza una posición a la derecha en al grid
     */
    public void moveRight()
    {
        // si el movimiento a la derecha es legal
        if(canMoveRight()) {
            for(int col = DEFAULT_COLS - 1; col >= 0; col--) {
                for(int row = DEFAULT_ROWS - 1; row >= 0; row--) {
                    Block block = getObjectAt(row,col);
                    // si existe bloque en esa posición y no está apilado
                    if(block != null && !block.isStacked()) {
                        // movemos a la derecha en bloque y borramos el anterior
                        placePiece(block, row, col+1);
                        eraseCell(row,col); 
                        
                    
                    }
                }
            }
        }   
    }
    
    /**
     * Verifica que todos los elementos a la derecha de los bloques de una pieza
     * esten libres.
     * @return True si la pieza puede moverse a la derecha, False en caso contrario
     */
    private boolean canMoveRight()
    {
            boolean check = true;
            // almacenamos los bloques de la pieza en una colección
            ArrayList<Block> currentPiece = getBlocksAtGrid();
            // verificamos que para cada bloque haya una posición válida a su derecha
            for(Block pieceBlock : currentPiece) {
                int row = pieceBlock.getRow();
                int col = pieceBlock.getCol();
                if(col+1 < DEFAULT_COLS) {
                    Block nextBlock = getObjectAt(row,col+1);
                    if(nextBlock != null && nextBlock.isStacked()) {
                        check = false;
                    }
                } else {
                    check = false;
                }    
            }
            return check; 
        
    }
    
    /**
     * Mueve toda la pieza una posición a la izquierda en el grid
     */
    public void moveLeft()
    {
         // si el movimiento a la izquierda es legal
         if(canMoveLeft()) {
            for(int col = 0; col <= DEFAULT_COLS - 1; col++) {
                for(int row = DEFAULT_ROWS - 1; row >= 0; row--) {
                    Block block = getObjectAt(row,col);
                    // si existe bloque en esa posición y no está apilado
                    if(block != null && !block.isStacked()) {
                        // movemos el bloque a la izquierda y borramos anterior
                        placePiece(block, row, col-1);
                        eraseCell(row,col);
                      
                    }
                }
            }
        }   
    }
    
    /**
     * Verifica que todos los elementos a la derecha de los bloques de una pieza
     * esten libres.
     * @return True si la pieza puede moverse a la izquierda, False en caso contrario
     */
    private boolean canMoveLeft()
    {
            boolean check = true;
            // almacenamos los bloques de la pieza en una colección
            ArrayList<Block> currentPiece = getBlocksAtGrid();
            // verificamos que para cada bloque haya una posición válida a su izquierda
            for(Block pieceBlock : currentPiece) {
                int row = pieceBlock.getRow();
                int col = pieceBlock.getCol();
                if(col-1 > -1) {
                    Block nextBlock = getObjectAt(row,col-1);
                    if(nextBlock != null && nextBlock.isStacked()) {
                        check = false;
                    }
                } else {
                    check = false;
                }    
            }
            return check;
    }
    
    /**
     * Desciende toda la pieza una posición en la matriz
     */
    public void moveDown()
    {
        // si el movimiento descendente es legal
        if(canMoveDown()) {
            for(int row = DEFAULT_ROWS - 1; row >= 0; row--) {
                for(int col = DEFAULT_COLS - 1; col >= 0; col--) {
                    Block block = getObjectAt(row,col);
                    // Si hay un bloque y éste no está apilado
                    if(block != null && !block.isStacked()) {
                        // bajamos una fila el bloque y borramos anterior
                        placePiece(block, row + 1, col);
                        eraseCell(row,col);
                    }
                }
            }
        } else {
            // Marcar todos los bloques como apilados
            setStacked();
            // el actual tetrimino se mezcla con el resto de bloques apilados
            // de modo que ya no existe como pieza
            tetrimino = null;
        }
    }
    
    /**
     * Verifica que todos los elementos por debajo de los bloques de una pieza
     * esten libres.
     * @return True si la pieza puede descender, False en caso contrario
     */
    private boolean canMoveDown()
    {
            boolean check = true;
            ArrayList<Block> currentPiece = getBlocksAtGrid();
            for(Block pieceBlock : currentPiece) {
                int row = pieceBlock.getRow();
                int col = pieceBlock.getCol();
                if(row+1 < DEFAULT_ROWS) {
                    Block nextBlock = getObjectAt(row+1,col);
                    if(nextBlock != null && nextBlock.isStacked()) {
                        check = false;
                    }
                } else {
                    check = false;
                }    
            }
            return check;  
        
    }
    
    /**
     * Intenta girar la pieza 90º si hay espacio para tal movimiento
     */
    public void turnPiece()
    {
        // si tiene espacio para girar, no ha girado ya 4 veces y no es ni palo ni cubo
        if(perimeterFree() && tetrimino.getPositions() > 2 && turns < 4) {
            // "cortamos" los bloques de su posición original
            Block zero0 = cutBlock(central.getRow()-1, central.getCol()-1);
            Block zero1 = cutBlock(central.getRow()-1, central.getCol());
            Block zero2 = cutBlock(central.getRow()-1, central.getCol()+1);
            Block one0 = cutBlock(central.getRow(), central.getCol()-1);
            Block one1 = central;
            Block one2 = cutBlock(central.getRow(), central.getCol()+1);
            Block two0 = cutBlock(central.getRow()+1, central.getCol()-1);
            Block two1 = cutBlock(central.getRow()+1, central.getCol());
            Block two2 = cutBlock(central.getRow()+1, central.getCol()+1);
            
            // los colocamos en sus nuevas posiciones excepto el central que no varía
            placePiece(two0,central.getRow()-1, central.getCol()-1);
            placePiece(one0,central.getRow()-1, central.getCol());
            placePiece(zero0,central.getRow()-1, central.getCol()+1);
            placePiece(two1,central.getRow(), central.getCol()-1);
            placePiece(zero1,central.getRow(), central.getCol()+1);
            placePiece(two2,central.getRow()+1, central.getCol()-1);
            placePiece(one2,central.getRow()+1, central.getCol());
            placePiece(zero2,central.getRow()+1, central.getCol()+1);
            // actualizamos el contador de giros
            turns++; 
        // en el caso del tetrimino "stick"
        }  else if (stickPerimeterFree() && tetrimino.getPositions() == 2 && turns < 4) {
            turnStick();
            turns++;
        }    
    }
    
    /**
     * Verifica que todas las posiciones alrededor del bloque central
     * sean válidas para poder realizar los giros correctamente
     * @return True si tiene un perímetro libre para realizar el giro, false en caso contrario
     */
    private boolean perimeterFree()
    {
        boolean check = true;
        for(int offsetY = central.getRow()-1; offsetY <= central.getRow()+1; offsetY++) {
            for(int offsetX = central.getCol()-1; offsetX <= central.getCol()+1; offsetX++) {
                // si las posiciones están dentro de los límites válidos
                if(offsetY >= 0 && offsetY < DEFAULT_ROWS && offsetX >= 0 && offsetX < DEFAULT_COLS) {
                    Block block = getObjectAt(offsetY,offsetX);
                    // si en esa posicion hay un bloque y está apilado no puede girar
                    if(block != null && block.isStacked()) {
                        check = false;
                    }
                // si el perimetro está fuera de limites válidos no puede girar    
                } else {
                    check = false;
                }    
            }
        }
        return check;
    }
    
    /**
     * Rota la pieza específica "stick"
     */
    private void turnStick()
    {
        // "cortamos" los bloques de su posición orginal la cual puede ser:
        // (----) o bien:
        // ( |  )
        // ( |  )
        // ( |  )
        // ( |  )
        Block zero1 = cutBlock(central.getRow()-1, central.getCol());
        Block one0 = cutBlock(central.getRow(), central.getCol()-1);
        Block one1 = central;
        Block one2 = cutBlock(central.getRow(), central.getCol()+1);
        Block one3 = cutBlock(central.getRow(), central.getCol()+2);
        Block two1 = cutBlock(central.getRow()+1, central.getCol());
        Block three1 = cutBlock(central.getRow()+2, central.getCol());
        
        // les colocamos en su nueva posición girados 90º    
        placePiece(one0,central.getRow()-1, central.getCol());
        placePiece(one2,central.getRow()+1, central.getCol());
        placePiece(one3,central.getRow()+2, central.getCol());
        placePiece(zero1,central.getRow(), central.getCol()-1);
        placePiece(two1,central.getRow(), central.getCol()+1);
        placePiece(three1,central.getRow(), central.getCol()+2);
        
    }
    
    /**
     * Verifica que todas las posiciones alrededor del bloque central
     * sean válidas para poder realizar los giros correctamente para
     * el caso específico del stick
     */
    private boolean stickPerimeterFree()
    {
        boolean check = true;
        for(int offsetY = central.getRow()-1; offsetY <= central.getRow()+2; offsetY++) {
            for(int offsetX = central.getCol()-1; offsetX <= central.getCol()+2; offsetX++) {
                if(offsetY >= 0 && offsetY < DEFAULT_ROWS && offsetX >= 0 && offsetX < DEFAULT_COLS) {
                    Block block = getObjectAt(offsetY,offsetX);
                    if(block != null && block.isStacked()) {
                        check = false;
                    }
                } else {
                    check = false;
                }    
            }
        }
        return check;
    }
    
    /**
     * Verifica si existe una fila del grid completa con bloques
     * y los elimina en caso afirmativo (hacer "linea" en el juego original)
     * @param row La fila que queremos comprobar
     * @return True si está llena de bloques, False en caso contrario
     */
    private boolean line(int row)
    {
        boolean check = true;
        for(int col = 0; col < DEFAULT_COLS; col++) {
            // si hay alguna posición vacía o no apilada devuelve false
            if(getObjectAt(row,col) == null || !getObjectAt(row,col).isStacked()) {
                check = false;
            }    
        }
        // si la línea esta completa con bloques borra toda la línea
        if(check == true) {
            for(int col = 0; col < DEFAULT_COLS; col++) {
                eraseCell(row,col);
                //return check;
            }
        }
        return check;
    }
    
    /**
     * Recorre la matriz de abajo a arriba buscando líneas completas
     * y eliminándolas, moviendo hacia abajo los bloques superiores
     */
    public void searchLines()
    {
        for(int checkRow = DEFAULT_ROWS-1; checkRow > 0; checkRow--) {
            // nos aseguramos que mientras existan "líneas" se eliminen y se bajen los de encima
            while(line(checkRow) == true) {
                // desde la fila inmediatamente superior a la cual en la que hizo "linea"
                for(int row = checkRow-1; row >= 0; row--) {
                    for(int col = 0; col < DEFAULT_COLS; col++) {
                        Block block = getObjectAt(row,col);
                        if(block != null && row+1 < DEFAULT_ROWS) {
                            // bajamos el bloque una fila y eliminamos el anterior
                            placePiece(block, row+1, col);
                            eraseCell(row,col);
                        }
                    }
                }
            }
        }
    }
    
   
}
