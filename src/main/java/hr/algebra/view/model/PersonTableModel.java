/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;


import hr.algebra.model.Person;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lorenababic
 */
public class PersonTableModel extends AbstractTableModel {
    
    private static final String[] COLUMN_NAMES = { "Id", "Name", "Role" };
    
    private List<Person> people;

    public PersonTableModel(List<Person> people) {
        this.people = people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Override
    public int getRowCount() {
        return people.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 -> {
                return people.get(rowIndex).getId();
            }
            case 1 -> {
                return people.get(rowIndex).getName();
            }
            case 2 -> {
                return people.get(rowIndex).getRole().name();
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
