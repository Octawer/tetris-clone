import java.awt.Color;

/**
 * Representa cada uno de los 4 bloques individuales que conforman cada
 * pieza del juego o tetrimino
 * 
 * @author Octavio Mart�nez
 * @version 16.05.2011
 */
public class Block
{
    // cada bloque almacena su posici�n en fila y columna
    private int row, col;
    // Cada bloque individual debe contener informaci�n sobre su color
    private Color blockColor;
    // Determina si el bloque ha tocado el fondo de la matriz de juego y no se debe mover
    private boolean stacked;
    

    /**
     * Constructor de bloques por defecto con color predefinido
     */
    public Block()
    {
        stacked = false;
        blockColor = Color.gray;
    }
        
    /**
     * Constructor de los bloques que formas las piezas, que establece 
     * un color definido por par�metro
     * @param color El color que establecemos para este bloque
     */
    public Block(Color color)
    {
        stacked = false;
        blockColor = color;
    }
    
    /**
     * Construye un bloque con un color y una posici�n determinadas
     * @param color El color que asignamos a este bloque
     * @param row La fila que asignamos a este bloque
     * @param col La columna que asignamos a este bloque
     */
    public Block(Color color, int row, int col)
    {
        stacked = false;
        blockColor = color;
        this.row = row;
        this.col = col;
    }
    
    /**
     * Establece una posici�n para este bloque en forma de coordenada
     * fila-columna
     * @param row La fila que queremos establecer
     * @param col La columna que queremos establecer
     */
    public void setLocation(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Acceso a la posici�n fila del bloque
     * @return La fila que almacena internamente el bloque
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Acceso a la posici�n columna del bloque
     * @return La columna que almacena internamente el bloque
     */
    public int getCol()
    {
        return col;
    }    
   
    
    /**
     * Establece el color de los bloques
     * @param color El color que queremos para este bloque particular
     */
    public void setBlockColor(Color color)
    {
        blockColor = color;
    }
    
    /**
     * Acceso al color del bloque
     * @return El color del bloque
     */
    public Color getBlockColor()
    {
        return blockColor;
    }
    
    /**
     * Indica que el bloque ha sido apilado en el fondo del grid
     * y no se puede mover (excepto en caso de hacer l�nea)
     */
    public void setStacked()
    {
        stacked = true;
    }
    
    /**
     * Verifica si el bloque est� o no apilado al fondo
     * @return True si est� apilado al fondo, false si no lo est�.
     */
    public boolean isStacked()
    {
        return stacked;
    }
    
}
