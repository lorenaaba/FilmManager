/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Film;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lorenababic
 */
public class FilmTableModel extends AbstractTableModel {
    
    private static final String[] COLUMN_NAMES = {"Id", "Title", "Duration","Published date", "Description", "Picture path"};
    
    private List<Film> films;

    public FilmTableModel(List<Film> films) {
        this.films = films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
        fireTableDataChanged();
    }
  
    @Override
    public int getRowCount() {
        return films.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        switch(columnIndex)
        {
            case 0 -> {
                return films.get(rowIndex).getId();
            }
            case 1 -> {
                return films.get(rowIndex).getTitle();
            }
            case 2 -> {
                return films.get(rowIndex).getDuration();
            }
            case 3 -> {
                return films.get(rowIndex).getPublishedDate().format(Film.DATE_FORMATTER);
            }
            case 4 -> {
                return films.get(rowIndex).getDescription();
            }            
            case 5 -> {
                return films.get(rowIndex).getPicturePath();
            }
            default -> throw new RuntimeException("No such column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0 -> {
                return Integer.class;
            }
        }
        return super.getColumnClass(columnIndex);        
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
       
}
