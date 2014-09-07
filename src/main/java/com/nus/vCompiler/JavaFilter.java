package com.nus.vCompiler;
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TiepCyber
 */
public class JavaFilter extends javax.swing.filechooser.FileFilter
{
  @Override
public boolean accept (File f) {
    return f.getName ().toLowerCase ().endsWith (".vhd")
           //||f.getName ().toLowerCase ().endsWith (".vcd")
          || f.isDirectory ();
  }
  
  @Override
public String getDescription () {
    return "VDHL files (*.vhd)";
  }
} // class JavaFilter