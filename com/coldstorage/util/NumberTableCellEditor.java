package com.coldstorage.util;

import java.awt.Component;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class NumberTableCellEditor extends javax.swing.AbstractCellEditor implements
TableCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField component = new JTextField();

	public NumberTableCellEditor(JFormattedTextField jtf)
	{
		  this.component=jtf;
	}
	
	public NumberTableCellEditor(JTextField jtf)
	{
		  this.component=jtf;
	}
	 
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value==null)
		     value="";
		component.setText(value.toString());
		component.setSelectionStart(0);
		component.setSelectionEnd(value.toString().length());
		
		return component;
	}

	 
	public Object getCellEditorValue() {
		return component.getText();
	}
}