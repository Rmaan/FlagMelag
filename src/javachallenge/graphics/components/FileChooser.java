package javachallenge.graphics.components;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public abstract class FileChooser extends JFileChooser {
	private ArrayList<String> types;
	private String description;
	
	public FileChooser(String title, String defaultPath, String types, String description) {
		this.types = new ArrayList<String>(Arrays.asList(types.split("[|]")));
		this.description = description;
		setDialogTitle(title);
		setCurrentDirectory(new File(defaultPath));
		
		FileFilter fileFilter = new FileFilter() {
			public String getDescription() {
				return FileChooser.this.description;
			}
			public String getExt (String filename) {
				String[] parts = filename.split("[\\.]");
				return parts[parts.length - 1];
			}
			public boolean accept (File file) {
				return file.isDirectory() || FileChooser.this.types.contains (getExt(file.getName()));
			}
		};
		setFileFilter(fileFilter);
	}
	
	public int showOpenDialog() {
		int returnVal = showOpenDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION)
			onAccept(getSelectedFile().getAbsolutePath());
		return returnVal;
	}
	
	public int showSaveDialog() {
		int returnVal = showSaveDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION)
			onAccept(getSelectedFile().getAbsolutePath());
		return returnVal;
	}
	
	public abstract void onAccept (String filename);
	
	public static abstract class FileChooserButton extends ClickableLabel {
		private boolean open; // true: open, false: save
		private FileChooser fileChooser;
		public FileChooserButton (String text, boolean open, 
				String title, String defaultPath, String types, String description) {
			super(text);
			fileChooser = new FileChooser(title, defaultPath, types, description) {
				@Override
				public void onAccept(String filename) {
					//if (!filename.endsWith("." + FileChooser.this.types.get(0)))
					//	filename += ".map";
					FileChooserButton.this.onAccept(filename);
				}
			};
		}
		
		public FileChooserButton (ImageIcon icon, boolean open, 
				String title, String defaultPath, String types, String description) {
			this ("", open, title, defaultPath, types, description);
			setIcon(icon);
		}

		@Override
		public void onClick() {
			if (open)
				fileChooser.showOpenDialog();
			else
				fileChooser.showSaveDialog();
		}
		
		public abstract void onAccept(String filename);
		
		public String getFileName() {
			return fileChooser.getSelectedFile().getName();
		}
	}
}