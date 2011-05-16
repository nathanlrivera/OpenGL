package a5;

import graphicslib3D.CameraSettingsDialog;
import graphicslib3D.ISurfacePatch;
import graphicslib3D.QuarterPatchDialog;
import graphicslib3D.SurfacePatchDialog;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import javax.swing.JTabbedPane;
import javax.media.opengl.GLCanvas;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import mathlib3D.Point3D;
import mathlib3D.Vector3D;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSlider;
import com.sun.opengl.util.Animator;

public class DisplayFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JPanel content = null;

	private JMenuBar menu = null;

	private JMenu fileMenu = null;

	private JMenuItem exitItem = null;

	private JMenuItem loadSceneItem = null;

	private JMenuItem saveSceneItem = null;

	private JTabbedPane tabs = null;

	private JPanel cameraTab = null;

	private GLCanvas canvas = null;

	private JRadioButton relativeModeButton = null;

	private JRadioButton absoluteModeButton = null;

	private ButtonGroup cameraButtonGroup = null; // @jve:decl-index=0:visual-constraint=""

	private JPanel modelTab = null;

	private Camera myCamera; // @jve:decl-index=0:

	private JButton modifyCameraButton = null;

	private CameraSettingsDialog cameraSettings;

	private JPanel slerpPanel = null;

	private JLabel beginLocationLabel = null;

	private JLabel beginOrientationLabel = null;

	private JTextField beginLocationTextField = null;

	private JTextField beginOrientationTextField = null;

	private JLabel endLocationLabel = null;

	private JLabel endOrientationLabel = null;

	private JTextField endLocationTextField = null;

	private JTextField endOrientationTextField = null;

	private JLabel slerpTimeLabel = null;

	private JTextField slerpTimeTextField = null;

	private JButton slerpButton = null;

	private JPanel lightPanel = null;

	private JPanel materialPanel = null;

	private JButton defineSurfacePatchButton = null;

	private JButton defineQuarterPatchButton = null;

	private JLabel selectPatchLabel = null;

	private JScrollPane patchScrollPane = null;

	private JList patchList = null;

	private JButton editPatchButton = null;

	private Hashtable<String, Visual> worldObjects; // @jve:decl-index=0:

	private DefaultListModel patchListModel;

	private JLabel editTransformLabel = null;

	private JTextField scaleTextField = null;

	private JButton scaleButton = null;

	private JTextField rotateTextField = null;

	private JButton rotateButton = null;

	private JButton translateButton = null;

	private JTextField translateTextField = null;

	private JMenuItem newSceneItem = null;

	private JButton setAmbientLightButton = null;

	private JMenu optionsMenu = null;

	private JCheckBoxMenuItem enableLightItem = null;

	private Hashtable<String, Light> lights; // @jve:decl-index=0:

	private JCheckBoxMenuItem smoothShadingMenuItem = null;

	private JButton addTeapotButton = null;

	private JButton addDistantLightButton = null;

	private JLabel shininessLabel = null;

	private JButton addMaterialButton = null;

	private JSlider shininessSlider = null;

	private JScrollPane lightScrollPane = null;

	private JList lightList = null;

	private JScrollPane materialScrollPane = null;

	private JList materialList = null;

	private DefaultListModel lightListModel = null;

	private DefaultListModel materialListModel = null;
	
	private DefaultListModel shaderListModel = null;

	private Hashtable<String, Material> materials; // @jve:decl-index=0:

	private JLabel editMaterialLabel = null;

	private JButton setAmbientButton = null;

	private JButton setDiffuseButton = null;

	private JButton setSpecularButton = null;

	private JButton setEmissionButton = null;

	private JScrollPane setModelMaterialScrollPane = null;

	private JList setModelMaterialList = null;

	private JLabel setModelMaterialLabel = null;

	private JLabel selectLightLabel = null;

	private JButton addPositionalLightButton = null;

	private JButton addSpotLightButton = null;

	private JButton editLightAmbientButton = null;

	private JButton editLightDiffuseButton = null;

	private JButton editLightSpecularButton = null;

	private JSlider editLightCutoffSlider = null;

	private JSlider editLightExponentSlider = null;

	private JLabel editLightCutoffLabel = null;

	private JLabel editExponentLabel = null;

	private JButton editLightDirectionButton = null;

	private JButton editLightLocationButton = null;

	private JButton editLinearButton = null;

	private JButton editConstantButton = null;

	private JButton editQuadraticButton = null;

	private JTextField editLightDirectionTextField = null;

	private JTextField editLightPositionTextField = null;

	private JTextField editConstantTextField = null;

	private JTextField editLinearTextField = null;

	private JTextField editQuadraticTextField = null;

	private JPanel shaderTab = null;
	
	private Hashtable<String, Shader> shaders;  //  @jve:decl-index=0:

	private JButton newShaderButton = null;

	private JLabel selectShaderModelLabel = null;

	private JScrollPane selectShaderModelScrollPane = null;

	private JList selectShaderModelList = null;

	private JLabel selectShaderLabel = null;

	private JScrollPane selectShaderScrollPane = null;

	private JList selectShaderList = null;

	private Animator animation;

	/**
	 * This is the default constructor
	 */
	public DisplayFrame()
	{
		super();
		initialize();
		System.out.println("Java Runtime Version " + System.getProperty("java.version"));
		System.out.println("JOGL Version " + canvas.getClass().getPackage().getImplementationVersion());
		animation = new Animator(getCanvas());
		animation.start();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		myCamera = new Camera();
		worldObjects = new Hashtable<String, Visual>();
		lights = new Hashtable<String, Light>();
		materials = new Hashtable<String, Material>();
		shaders = new Hashtable<String, Shader>();
		patchListModel = new DefaultListModel();
		lightListModel = new DefaultListModel();
		materialListModel = new DefaultListModel();
		shaderListModel = new DefaultListModel();
		cameraSettings = new CameraSettingsDialog(this);
		cameraSettings.setDefaultValues(myCamera.getLocation(), myCamera.getLookAtPoint(), myCamera.getUpDirection());
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		this.setTitle("CSC 155 Assignment 5");
		this.setContentPane(getContent());
		this.setJMenuBar(getMenu());
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes content
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getContent()
	{
		if(content == null)
		{
			content = new JPanel();
			content.setLayout(new BorderLayout());
			content.add(getCanvas(), BorderLayout.CENTER);
			content.add(getTabs(), BorderLayout.WEST);
			getCameraButtonGroup();
		}
		return content;
	}

	/**
	 * This method initializes menu
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getMenu()
	{
		if(menu == null)
		{
			menu = new JMenuBar();
			menu.add(getFileMenu());
			menu.add(getOptionsMenu());
		}
		return menu;
	}

	/**
	 * This method initializes fileMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu()
	{
		if(fileMenu == null)
		{
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.setMnemonic(KeyEvent.VK_F);
			fileMenu.add(getNewSceneItem());
			fileMenu.add(getLoadSceneItem());
			fileMenu.add(getSaveSceneItem());
			fileMenu.add(getExitItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes exitItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitItem()
	{
		if(exitItem == null)
		{
			exitItem = new JMenuItem();
			exitItem.setText("Exit");
			exitItem.setActionCommand("Exit");
			exitItem.setMnemonic(KeyEvent.VK_X);
			exitItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					System.exit(0);
				}
			});
		}
		return exitItem;
	}

	/**
	 * This method initializes loadSceneItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getLoadSceneItem()
	{
		if(loadSceneItem == null)
		{
			loadSceneItem = new JMenuItem();
			loadSceneItem.setText("Open Scene");
			loadSceneItem.setMnemonic(KeyEvent.VK_O);
			loadSceneItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION)
					{
						clearScene();
						try
						{
							FileInputStream myFIS = new FileInputStream(fc.getSelectedFile());
							ObjectInputStream myOIS = new ObjectInputStream(myFIS);
							int worldObjectCount = myOIS.readInt();
							for(int i = 0; i < worldObjectCount; ++i)
							{
								Visual v = (Visual) myOIS.readObject();
								worldObjects.put(v.getName(), v);
								patchListModel.addElement(v.getName());
							}
							int lightCount = myOIS.readInt();
							for(int i = 0; i < lightCount; ++i)
							{
								Light l = (Light) myOIS.readObject();
								lights.put(l.getName(), l);
								lightListModel.addElement(l.getName());
							}
							int materialCount = myOIS.readInt();
							for(int i = 0; i < materialCount; ++i)
							{
								Material m = (Material) myOIS.readObject();
								materials.put(m.getName(), m);
								materialListModel.addElement(m.getName());
							}
							int shaderCount = myOIS.readInt();
							for(int i = 0; i < shaderCount; ++i)
							{
								Shader s = (Shader) myOIS.readObject();
								s.resetIDs();
								shaders.put(s.getName(), s);
								shaderListModel.addElement(s.getName());
							}
							AmbientLight.getInstance().setIntensity((Color) myOIS.readObject());
							myOIS.close();
						}
						catch(FileNotFoundException e1)
						{
							JOptionPane.showMessageDialog(null, "File not found.", "File Not Found", JOptionPane.ERROR_MESSAGE);
						}
						catch(IOException e2)
						{
							JOptionPane.showMessageDialog(null, "An error occurred while reading from the file.", "I/O Error", JOptionPane.ERROR_MESSAGE);
						}
						catch(ClassNotFoundException e3)
						{
							JOptionPane.showMessageDialog(null, "An error occurred while loading the scene.", "Scene Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return loadSceneItem;
	}

	/**
	 * This method initializes saveSceneItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveSceneItem()
	{
		if(saveSceneItem == null)
		{
			saveSceneItem = new JMenuItem();
			saveSceneItem.setMnemonic(KeyEvent.VK_S);
			saveSceneItem.setText("Save Scene");
			saveSceneItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						try
						{
							FileOutputStream myFOS = new FileOutputStream(fc.getSelectedFile());
							ObjectOutputStream myOOS = new ObjectOutputStream(myFOS);
							myOOS.writeInt(worldObjects.size());
							for(Visual v : worldObjects.values())
								myOOS.writeObject(v);
							myOOS.writeInt(lights.size());
							for(Light l : lights.values())
								myOOS.writeObject(l);
							myOOS.writeInt(materials.size());
							for(Material m : materials.values())
								myOOS.writeObject(m);
							myOOS.writeInt(shaders.size());
							for(Shader s : shaders.values())
								myOOS.writeObject(s);
							myOOS.writeObject(AmbientLight.getInstance().getIntensity());
							myOOS.close();
						}
						catch(FileNotFoundException e1)
						{
							JOptionPane.showMessageDialog(null, "File not found.", "File Not Found", JOptionPane.ERROR_MESSAGE);
						}
						catch(IOException e2)
						{
							JOptionPane.showMessageDialog(null, "An error occurred while writing to the file.", "I/O Error", JOptionPane.ERROR_MESSAGE);
						}
				}
			});
		}
		return saveSceneItem;
	}

	/**
	 * This method initializes tabs
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabs()
	{
		if(tabs == null)
		{
			tabs = new JTabbedPane();
			tabs.addTab("Shaders", null, getShaderTab(), null);
			tabs.addTab("Model", null, getModelTab(), null);
			tabs.addTab("Lights", null, getLightPanel(), null);
			tabs.addTab("Materials", null, getMaterialPanel(), null);
			tabs.addTab("Camera", null, getCameraTab(), null);
			tabs.addTab("SLERP", null, getSlerpPanel(), null);
		}
		return tabs;
	}

	/**
	 * This method initializes cameraTab
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCameraTab()
	{
		if(cameraTab == null)
		{
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints13.weightx = 0.0;
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.anchor = GridBagConstraints.CENTER;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints12.anchor = GridBagConstraints.NORTH;
			gridBagConstraints12.gridy = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints11.anchor = GridBagConstraints.CENTER;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 1;
			cameraTab = new JPanel();
			cameraTab.setLayout(new GridBagLayout());
			cameraTab.add(getAbsoluteModeButton(), gridBagConstraints13);
			cameraTab.add(getRelativeModeButton(), gridBagConstraints11);
			cameraTab.add(getModifyCameraButton(), gridBagConstraints12);
		}
		return cameraTab;
	}

	/**
	 * This method initializes canvas
	 * 
	 * @return javax.media.opengl.GLCanvas
	 */
	private GLCanvas getCanvas()
	{
		if(canvas == null)
		{
			canvas = new GLCanvas();
			canvas.addGLEventListener(new GLCanvasListener(myCamera, worldObjects, getEnableLightItem(), getSmoothShadingMenuItem(), lights));
			MouseInput mouseInput = new MouseInput(myCamera);
			canvas.addMouseListener(mouseInput);
			canvas.addMouseMotionListener(mouseInput);
			canvas.addMouseWheelListener(mouseInput);
			canvas.addKeyListener(new KeyboardInput(myCamera));
		}
		return canvas;
	}

	/**
	 * This method initializes relativeModeButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRelativeModeButton()
	{
		if(relativeModeButton == null)
		{
			relativeModeButton = new JRadioButton();
			relativeModeButton.setText("Relative Mode");
			relativeModeButton.setName("relativeModeButton");
			relativeModeButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					modifyCameraButton.setEnabled(false);
					myCamera.setRelativeMode(true);
				}
			});
		}
		return relativeModeButton;
	}

	/**
	 * This method initializes absoluteModeButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getAbsoluteModeButton()
	{
		if(absoluteModeButton == null)
		{
			absoluteModeButton = new JRadioButton();
			absoluteModeButton.setText("Absolute Mode");
			absoluteModeButton.setName("absoluteModeButton");
			absoluteModeButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					modifyCameraButton.setEnabled(true);
					myCamera.setRelativeMode(false);
				}
			});
		}
		return absoluteModeButton;
	}

	/**
	 * This method initializes cameraButtonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getCameraButtonGroup()
	{
		if(cameraButtonGroup == null)
		{
			cameraButtonGroup = new ButtonGroup();
			cameraButtonGroup.add(getRelativeModeButton());
			cameraButtonGroup.add(getAbsoluteModeButton());
			getRelativeModeButton().setSelected(true);
		}
		return cameraButtonGroup;
	}

	/**
	 * This method initializes modelTab
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getModelTab()
	{
		if(modelTab == null)
		{
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridx = 0;
			gridBagConstraints36.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints36.gridwidth = 2;
			gridBagConstraints36.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints36.gridy = 11;
			setModelMaterialLabel = new JLabel();
			setModelMaterialLabel.setText("Set Material for Selected Object:");
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints35.gridy = 12;
			gridBagConstraints35.weightx = 0.0;
			gridBagConstraints35.weighty = 1.0;
			gridBagConstraints35.gridwidth = 2;
			gridBagConstraints35.anchor = GridBagConstraints.NORTH;
			gridBagConstraints35.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints35.gridx = 0;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridx = 0;
			gridBagConstraints27.gridwidth = 2;
			gridBagConstraints27.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints27.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints27.gridy = 0;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.gridy = 10;
			gridBagConstraints25.weightx = 0.0;
			gridBagConstraints25.anchor = GridBagConstraints.CENTER;
			gridBagConstraints25.insets = new Insets(5, 2, 2, 2);
			gridBagConstraints25.weighty = 0.0;
			gridBagConstraints25.gridx = 0;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 1;
			gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints24.insets = new Insets(5, 2, 2, 2);
			gridBagConstraints24.anchor = GridBagConstraints.NORTH;
			gridBagConstraints24.weighty = 0.0;
			gridBagConstraints24.gridy = 10;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 1;
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.insets = new Insets(5, 2, 2, 2);
			gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.gridy = 9;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.gridy = 9;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.anchor = GridBagConstraints.EAST;
			gridBagConstraints22.insets = new Insets(5, 2, 2, 2);
			gridBagConstraints22.weighty = 0.0;
			gridBagConstraints22.gridx = 0;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.gridy = 8;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.gridy = 8;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.anchor = GridBagConstraints.EAST;
			gridBagConstraints20.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.insets = new Insets(10, 2, 2, 2);
			gridBagConstraints19.gridheight = 1;
			gridBagConstraints19.gridwidth = 2;
			gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.gridy = 7;
			editTransformLabel = new JLabel();
			editTransformLabel.setText("Edit Transforms for Selected Object:");
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints18.fill = GridBagConstraints.NONE;
			gridBagConstraints18.gridwidth = 2;
			gridBagConstraints18.gridy = 6;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.BOTH;
			gridBagConstraints17.gridy = 5;
			gridBagConstraints17.weightx = 0.0;
			gridBagConstraints17.weighty = 0.0;
			gridBagConstraints17.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints17.gridwidth = 2;
			gridBagConstraints17.gridx = 0;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.insets = new Insets(20, 2, 2, 2);
			gridBagConstraints16.gridwidth = 2;
			gridBagConstraints16.gridy = 4;
			selectPatchLabel = new JLabel();
			selectPatchLabel.setText("Select An Object to Modify:");
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.anchor = GridBagConstraints.NORTH;
			gridBagConstraints15.weighty = 0.0;
			gridBagConstraints15.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridwidth = 2;
			gridBagConstraints15.gridy = 2;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.gridwidth = 2;
			gridBagConstraints14.weighty = 0.0;
			gridBagConstraints14.gridy = 1;
			modelTab = new JPanel();
			modelTab.setLayout(new GridBagLayout());
			modelTab.add(getDefineSurfacePatchButton(), gridBagConstraints14);
			modelTab.add(getDefineQuarterPatchButton(), gridBagConstraints15);
			modelTab.add(selectPatchLabel, gridBagConstraints16);
			modelTab.add(getPatchScrollPane(), gridBagConstraints17);
			modelTab.add(getEditPatchButton(), gridBagConstraints18);
			modelTab.add(editTransformLabel, gridBagConstraints19);
			modelTab.add(getScaleTextField(), gridBagConstraints20);
			modelTab.add(getScaleButton(), gridBagConstraints21);
			modelTab.add(getRotateTextField(), gridBagConstraints22);
			modelTab.add(getRotateButton(), gridBagConstraints23);
			modelTab.add(getTranslateButton(), gridBagConstraints24);
			modelTab.add(getTranslateTextField(), gridBagConstraints25);
			modelTab.add(getAddTeapotButton(), gridBagConstraints27);
			modelTab.add(getSetModelMaterialScrollPane(), gridBagConstraints35);
			modelTab.add(setModelMaterialLabel, gridBagConstraints36);
		}
		return modelTab;
	}

	/**
	 * This method initializes modifyCameraButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getModifyCameraButton()
	{
		if(modifyCameraButton == null)
		{
			modifyCameraButton = new JButton();
			modifyCameraButton.setText("Change Camera Parameters");
			modifyCameraButton.setEnabled(false);
			modifyCameraButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					cameraSettings.setCameraLocation(myCamera.getLocation());
					cameraSettings.setCameraLookAt(myCamera.getLookAtPoint());
					cameraSettings.setCameraUp(myCamera.getUpDirection());
					cameraSettings.setVisible(true);
					if(cameraSettings.getStatus() == CameraSettingsDialog.USER_RESET)
						cameraSettings.resetCameraSettings();
					if(cameraSettings.getStatus() != CameraSettingsDialog.USER_CANCEL)
					{
						myCamera.setLocation(cameraSettings.getCameraLocation());
						myCamera.setLookAtPoint(cameraSettings.getCameraLookAt());
						myCamera.setUpDirection(cameraSettings.getCameraUp());
					}
				}
			});
		}
		return modifyCameraButton;
	}

	/**
	 * This method initializes slerpPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSlerpPanel()
	{
		if(slerpPanel == null)
		{
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.anchor = GridBagConstraints.NORTH;
			gridBagConstraints10.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weighty = 1.0;
			gridBagConstraints10.gridy = 5;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridy = 4;
			gridBagConstraints9.weightx = 0.0;
			gridBagConstraints9.anchor = GridBagConstraints.CENTER;
			gridBagConstraints9.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.gridy = 4;
			slerpTimeLabel = new JLabel();
			slerpTimeLabel.setText("Animation Time (secs)");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 0.0;
			gridBagConstraints7.anchor = GridBagConstraints.CENTER;
			gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.weightx = 0.0;
			gridBagConstraints6.anchor = GridBagConstraints.CENTER;
			gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridy = 3;
			endOrientationLabel = new JLabel();
			endOrientationLabel.setText("End Orientation (deg,x,y,z)");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.gridy = 2;
			endLocationLabel = new JLabel();
			endLocationLabel.setText("End Location (x,y,z)");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 0.0;
			gridBagConstraints3.anchor = GridBagConstraints.CENTER;
			gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 0.0;
			gridBagConstraints2.anchor = GridBagConstraints.CENTER;
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridy = 1;
			beginOrientationLabel = new JLabel();
			beginOrientationLabel.setText("Start Orientation (deg,x,y,z)");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridy = 0;
			beginLocationLabel = new JLabel();
			beginLocationLabel.setText("Start Location (x,y,z)");
			slerpPanel = new JPanel();
			slerpPanel.setLayout(new GridBagLayout());
			slerpPanel.add(beginLocationLabel, gridBagConstraints);
			slerpPanel.add(beginOrientationLabel, gridBagConstraints1);
			slerpPanel.add(getBeginLocationTextField(), gridBagConstraints2);
			slerpPanel.add(getBeginOrientationTextField(), gridBagConstraints3);
			slerpPanel.add(endLocationLabel, gridBagConstraints4);
			slerpPanel.add(endOrientationLabel, gridBagConstraints5);
			slerpPanel.add(getEndLocationTextField(), gridBagConstraints6);
			slerpPanel.add(getEndOrientationTextField(), gridBagConstraints7);
			slerpPanel.add(slerpTimeLabel, gridBagConstraints8);
			slerpPanel.add(getSlerpTimeTextField(), gridBagConstraints9);
			slerpPanel.add(getSlerpButton(), gridBagConstraints10);
		}
		return slerpPanel;
	}

	/**
	 * This method initializes beginLocationTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBeginLocationTextField()
	{
		if(beginLocationTextField == null)
		{
			beginLocationTextField = new JTextField();
			beginLocationTextField.setPreferredSize(new Dimension(100, 20));
			beginLocationTextField.setText("0,0,800");
		}
		return beginLocationTextField;
	}

	/**
	 * This method initializes beginOrientationTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBeginOrientationTextField()
	{
		if(beginOrientationTextField == null)
		{
			beginOrientationTextField = new JTextField();
			beginOrientationTextField.setPreferredSize(new Dimension(100, 20));
			beginOrientationTextField.setText("1,0,0,0");
		}
		return beginOrientationTextField;
	}

	/**
	 * This method initializes endLocationTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEndLocationTextField()
	{
		if(endLocationTextField == null)
		{
			endLocationTextField = new JTextField();
			endLocationTextField.setPreferredSize(new Dimension(100, 20));
			endLocationTextField.setText("65,65,550");
		}
		return endLocationTextField;
	}

	/**
	 * This method initializes endOrientationTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEndOrientationTextField()
	{
		if(endOrientationTextField == null)
		{
			endOrientationTextField = new JTextField();
			endOrientationTextField.setPreferredSize(new Dimension(100, 20));
			endOrientationTextField.setText("180,0,0,1");
		}
		return endOrientationTextField;
	}

	/**
	 * This method initializes slerpTimeTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSlerpTimeTextField()
	{
		if(slerpTimeTextField == null)
		{
			slerpTimeTextField = new JTextField();
			slerpTimeTextField.setPreferredSize(new Dimension(100, 20));
			slerpTimeTextField.setText("2.0");
		}
		return slerpTimeTextField;
	}

	/**
	 * This method initializes slerpButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSlerpButton()
	{
		if(slerpButton == null)
		{
			slerpButton = new JButton();
			slerpButton.setText("Perform Animation");
			slerpButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String[] startPoints = beginLocationTextField.getText().split(",");
					Point3D start = new Point3D(Double.parseDouble(startPoints[0]), Double.parseDouble(startPoints[1]), Double.parseDouble(startPoints[2]));
					String[] endPoints = endLocationTextField.getText().split(",");
					Point3D end = new Point3D(Double.parseDouble(endPoints[0]), Double.parseDouble(endPoints[1]), Double.parseDouble(endPoints[2]));
					String[] startOrientationPoints = beginOrientationTextField.getText().split(",");
					Quaternion startQuaternion = new Quaternion(Double.parseDouble(startOrientationPoints[1]), Double.parseDouble(startOrientationPoints[2]), Double.parseDouble(startOrientationPoints[3]), Math.toRadians(Double.parseDouble(startOrientationPoints[0])));
					String[] endOrientationPoints = endOrientationTextField.getText().split(",");
					Quaternion endQuaternion = new Quaternion(Double.parseDouble(endOrientationPoints[1]), Double.parseDouble(endOrientationPoints[2]), Double.parseDouble(endOrientationPoints[3]), Math.toRadians(Double.parseDouble(endOrientationPoints[0])));
					SlerpAction listener = new SlerpAction(myCamera, start, startQuaternion, end, endQuaternion, Double.parseDouble(slerpTimeTextField.getText()));
					Timer slerpTimer = new Timer((int) (1000.0 / 60.0), listener);
					listener.setTimer(slerpTimer);
					absoluteModeButton.setSelected(false);
					relativeModeButton.setSelected(true);
					myCamera.setRelativeMode(true);
					myCamera.setLocation(start);
					myCamera.setOrientation(startQuaternion);
					slerpTimer.start();
				}
			});
		}
		return slerpButton;
	}

	/**
	 * This method initializes lightPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLightPanel()
	{
		if(lightPanel == null)
		{
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.fill = GridBagConstraints.BOTH;
			gridBagConstraints60.gridy = 14;
			gridBagConstraints60.weightx = 1.0;
			gridBagConstraints60.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints60.gridx = 0;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.fill = GridBagConstraints.BOTH;
			gridBagConstraints59.gridy = 13;
			gridBagConstraints59.weightx = 1.0;
			gridBagConstraints59.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints59.gridx = 0;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.fill = GridBagConstraints.BOTH;
			gridBagConstraints58.gridy = 12;
			gridBagConstraints58.weightx = 1.0;
			gridBagConstraints58.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints58.gridx = 0;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.fill = GridBagConstraints.BOTH;
			gridBagConstraints57.gridy = 11;
			gridBagConstraints57.weightx = 1.0;
			gridBagConstraints57.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints57.gridx = 0;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.fill = GridBagConstraints.BOTH;
			gridBagConstraints56.gridy = 10;
			gridBagConstraints56.weightx = 1.0;
			gridBagConstraints56.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints56.gridx = 0;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.gridx = 1;
			gridBagConstraints55.gridwidth = 1;
			gridBagConstraints55.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints55.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints55.gridy = 14;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridx = 1;
			gridBagConstraints54.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints54.gridwidth = 1;
			gridBagConstraints54.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints54.gridy = 12;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 1;
			gridBagConstraints53.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints53.gridwidth = 1;
			gridBagConstraints53.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints53.gridy = 13;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridx = 1;
			gridBagConstraints52.gridwidth = 1;
			gridBagConstraints52.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints52.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints52.gridy = 11;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.gridwidth = 1;
			gridBagConstraints51.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints51.gridy = 10;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.gridx = 0;
			gridBagConstraints50.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints50.weighty = 1.0;
			gridBagConstraints50.anchor = GridBagConstraints.NORTH;
			gridBagConstraints50.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints50.gridy = 16;
			editExponentLabel = new JLabel();
			editExponentLabel.setText("Exponent");
			editExponentLabel.setEnabled(false);
			editExponentLabel.setVisible(true);
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.gridx = 0;
			gridBagConstraints49.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints49.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints49.gridy = 15;
			editLightCutoffLabel = new JLabel();
			editLightCutoffLabel.setText("Cutoff Angle");
			editLightCutoffLabel.setEnabled(false);
			editLightCutoffLabel.setVisible(true);
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints48.gridy = 16;
			gridBagConstraints48.weightx = 0.0;
			gridBagConstraints48.weighty = 1.0;
			gridBagConstraints48.anchor = GridBagConstraints.NORTH;
			gridBagConstraints48.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints48.gridx = 1;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints47.gridy = 15;
			gridBagConstraints47.weightx = 1.0;
			gridBagConstraints47.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints47.gridx = 1;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridx = 0;
			gridBagConstraints46.gridwidth = 2;
			gridBagConstraints46.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints46.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints46.gridy = 9;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridx = 0;
			gridBagConstraints45.gridwidth = 2;
			gridBagConstraints45.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints45.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints45.gridy = 8;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridx = 0;
			gridBagConstraints44.gridwidth = 2;
			gridBagConstraints44.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints44.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints44.gridy = 7;
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.gridx = 0;
			gridBagConstraints43.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints43.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints43.gridwidth = 2;
			gridBagConstraints43.gridy = 4;
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints42.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints42.gridwidth = 2;
			gridBagConstraints42.gridy = 3;
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.gridx = 0;
			gridBagConstraints37.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints37.insets = new Insets(20, 2, 2, 2);
			gridBagConstraints37.gridwidth = 2;
			gridBagConstraints37.gridy = 5;
			selectLightLabel = new JLabel();
			selectLightLabel.setText("Select a Light to modify:");
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints40.gridy = 6;
			gridBagConstraints40.weightx = 0.0;
			gridBagConstraints40.weighty = 0.0;
			gridBagConstraints40.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints40.gridwidth = 2;
			gridBagConstraints40.anchor = GridBagConstraints.NORTH;
			gridBagConstraints40.gridx = 0;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridx = 0;
			gridBagConstraints28.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints28.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints28.gridwidth = 2;
			gridBagConstraints28.gridy = 2;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridwidth = 2;
			gridBagConstraints26.gridy = 0;
			lightPanel = new JPanel();
			lightPanel.setLayout(new GridBagLayout());
			lightPanel.add(getSetAmbientLightButton(), gridBagConstraints26);
			lightPanel.add(getAddDistantLightButton(), gridBagConstraints28);
			lightPanel.add(getLightScrollPane(), gridBagConstraints40);
			lightPanel.add(selectLightLabel, gridBagConstraints37);
			lightPanel.add(getAddPositionalLightButton(), gridBagConstraints42);
			lightPanel.add(getAddSpotLightButton(), gridBagConstraints43);
			lightPanel.add(getEditLightAmbientButton(), gridBagConstraints44);
			lightPanel.add(getEditLightDiffuseButton(), gridBagConstraints45);
			lightPanel.add(getEditLightSpecularButton(), gridBagConstraints46);
			lightPanel.add(getEditLightCutoffSlider(), gridBagConstraints47);
			lightPanel.add(getEditLightExponentSlider(), gridBagConstraints48);
			lightPanel.add(editLightCutoffLabel, gridBagConstraints49);
			lightPanel.add(editExponentLabel, gridBagConstraints50);
			lightPanel.add(getEditLightDirectionButton(), gridBagConstraints51);
			lightPanel.add(getEditLightLocationButton(), gridBagConstraints52);
			lightPanel.add(getEditLinearButton(), gridBagConstraints53);
			lightPanel.add(getEditConstantButton(), gridBagConstraints54);
			lightPanel.add(getEditQuadraticButton(), gridBagConstraints55);
			lightPanel.add(getEditLightDirectionTextField(), gridBagConstraints56);
			lightPanel.add(getEditLightPositionTextField(), gridBagConstraints57);
			lightPanel.add(getEditConstantTextField(), gridBagConstraints58);
			lightPanel.add(getEditLinearTextField(), gridBagConstraints59);
			lightPanel.add(getEditQuadraticTextField(), gridBagConstraints60);
		}
		return lightPanel;
	}

	/**
	 * This method initializes materialPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMaterialPanel()
	{
		if(materialPanel == null)
		{
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 0;
			gridBagConstraints34.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints34.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints34.gridwidth = 2;
			gridBagConstraints34.gridy = 6;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 0;
			gridBagConstraints32.gridwidth = 2;
			gridBagConstraints32.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints32.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints32.gridy = 5;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridwidth = 2;
			gridBagConstraints31.gridy = 4;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.gridwidth = 2;
			gridBagConstraints30.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints30.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.gridy = 3;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridx = 0;
			gridBagConstraints29.gridwidth = 2;
			gridBagConstraints29.anchor = GridBagConstraints.WEST;
			gridBagConstraints29.insets = new Insets(20, 2, 2, 2);
			gridBagConstraints29.gridy = 1;
			editMaterialLabel = new JLabel();
			editMaterialLabel.setText("Select a Material to edit:");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 2;
			gridBagConstraints41.weightx = 0.0;
			gridBagConstraints41.weighty = 0.0;
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints41.anchor = GridBagConstraints.CENTER;
			gridBagConstraints41.gridx = 0;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.fill = GridBagConstraints.NONE;
			gridBagConstraints33.gridy = 7;
			gridBagConstraints33.weightx = 0.0;
			gridBagConstraints33.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints33.ipady = 0;
			gridBagConstraints33.weighty = 1.0;
			gridBagConstraints33.anchor = GridBagConstraints.NORTH;
			gridBagConstraints33.gridx = 1;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridx = 0;
			gridBagConstraints39.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints39.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints39.gridheight = 1;
			gridBagConstraints39.gridwidth = 2;
			gridBagConstraints39.anchor = GridBagConstraints.CENTER;
			gridBagConstraints39.gridy = 0;
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.gridx = 0;
			gridBagConstraints38.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints38.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints38.ipady = 0;
			gridBagConstraints38.weighty = 1.0;
			gridBagConstraints38.anchor = GridBagConstraints.NORTH;
			gridBagConstraints38.gridy = 7;
			shininessLabel = new JLabel();
			shininessLabel.setText("Shininess");
			shininessLabel.setEnabled(false);
			materialPanel = new JPanel();
			materialPanel.setLayout(new GridBagLayout());
			materialPanel.add(shininessLabel, gridBagConstraints38);
			materialPanel.add(getAddMaterialButton(), gridBagConstraints39);
			materialPanel.add(getShininessSlider(), gridBagConstraints33);
			materialPanel.add(getMaterialScrollPane(), gridBagConstraints41);
			materialPanel.add(editMaterialLabel, gridBagConstraints29);
			materialPanel.add(getSetAmbientButton(), gridBagConstraints30);
			materialPanel.add(getSetDiffuseButton(), gridBagConstraints31);
			materialPanel.add(getSetSpecularButton(), gridBagConstraints32);
			materialPanel.add(getSetEmissionButton(), gridBagConstraints34);
		}
		return materialPanel;
	}

	/**
	 * This method initializes defineSurfacePatchButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDefineSurfacePatchButton()
	{
		if(defineSurfacePatchButton == null)
		{
			defineSurfacePatchButton = new JButton();
			defineSurfacePatchButton.setText("Define Freeform Surface Patch");
			defineSurfacePatchButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					SurfacePatchDialog dialog = new SurfacePatchDialog(null);
					SurfacePatch p = new SurfacePatch();
					if(dialog.show(p))
					{
						Visual visual = new Visual(p);
						worldObjects.put(p.getName(), visual);
						patchListModel.addElement(p.getName());
					}
				}
			});
		}
		return defineSurfacePatchButton;
	}

	/**
	 * This method initializes defineQuarterPatchButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDefineQuarterPatchButton()
	{
		if(defineQuarterPatchButton == null)
		{
			defineQuarterPatchButton = new JButton();
			defineQuarterPatchButton.setText("Define Quarter Surface of Revolution");
			defineQuarterPatchButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					QuarterPatchDialog dialog = new QuarterPatchDialog(null);
					QuarterPatch p = new QuarterPatch();
					if(dialog.show(p))
					{
						Visual visual = new Visual(p);
						worldObjects.put(p.getName(), visual);
						patchListModel.addElement(p.getName());
					}
				}
			});
		}
		return defineQuarterPatchButton;
	}

	/**
	 * This method initializes patchScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getPatchScrollPane()
	{
		if(patchScrollPane == null)
		{
			patchScrollPane = new JScrollPane();
			patchScrollPane.setViewportView(getPatchList());
		}
		return patchScrollPane;
	}

	/**
	 * This method initializes patchList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getPatchList()
	{
		if(patchList == null)
		{
			patchList = new JList();
			patchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			patchList.setModel(patchListModel);
			patchList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
			{
				public void valueChanged(javax.swing.event.ListSelectionEvent e)
				{
					if(patchList.getSelectedValue() == null)
						return;
					Visual v = worldObjects.get(patchList.getSelectedValue());
					getScaleTextField().setText(v.getScale().getX() + "," + v.getScale().getY() + "," + v.getScale().getZ());
					getRotateTextField().setText(v.getRotation().getX() + "," + v.getRotation().getY() + "," + v.getRotation().getZ());
					getTranslateTextField().setText(v.getTranslation().getX() + "," + v.getTranslation().getY() + "," + v.getTranslation().getZ());
					editPatchButton.setEnabled(v.getShape() instanceof ISurfacePatch);
					getSetModelMaterialList().clearSelection();
					getSetModelMaterialList().setSelectedValue(v.getMaterial().getName(), true);
				}
			});
		}
		return patchList;
	}

	/**
	 * This method initializes editPatchButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditPatchButton()
	{
		if(editPatchButton == null)
		{
			editPatchButton = new JButton();
			editPatchButton.setText("Edit Control Points");
			editPatchButton.setEnabled(false);
			editPatchButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Visual v = worldObjects.get(patchList.getSelectedValue());
					if(v != null)
					{
						SurfacePatch p = (SurfacePatch) v.getShape();
						if(p instanceof QuarterPatch)
						{
							QuarterPatchDialog dialog = new QuarterPatchDialog(null);
							if(dialog.show((QuarterPatch)p))
							{
							}
						}
						else
						{
							SurfacePatchDialog dialog = new SurfacePatchDialog(null);
							if(dialog.show(p))
							{
							}
						}
					}
				}
			});
		}
		return editPatchButton;
	}

	/**
	 * This method initializes scaleTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getScaleTextField()
	{
		if(scaleTextField == null)
		{
			scaleTextField = new JTextField();
			scaleTextField.setPreferredSize(new Dimension(100, 20));
			scaleTextField.setText("1.0,1.0,1.0");
		}
		return scaleTextField;
	}

	/**
	 * This method initializes scaleButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getScaleButton()
	{
		if(scaleButton == null)
		{
			scaleButton = new JButton();
			scaleButton.setText("Set Scale (x,y,z)");
			scaleButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Visual v = worldObjects.get(patchList.getSelectedValue());
					if(v != null)
					{
						String[] coords = scaleTextField.getText().split(",");
						v.setScale(new Vector3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
					}
				}
			});
		}
		return scaleButton;
	}

	/**
	 * This method initializes rotateTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getRotateTextField()
	{
		if(rotateTextField == null)
		{
			rotateTextField = new JTextField();
			rotateTextField.setPreferredSize(new Dimension(100, 20));
			rotateTextField.setText("0.0,0.0,0.0");
		}
		return rotateTextField;
	}

	/**
	 * This method initializes rotateButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRotateButton()
	{
		if(rotateButton == null)
		{
			rotateButton = new JButton();
			rotateButton.setText("Set Rotation (x,y,z)");
			rotateButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Visual v = worldObjects.get(patchList.getSelectedValue());
					if(v != null)
					{
						String[] coords = rotateTextField.getText().split(",");
						v.setRotation(new Vector3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
					}
				}
			});
		}
		return rotateButton;
	}

	/**
	 * This method initializes translateButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getTranslateButton()
	{
		if(translateButton == null)
		{
			translateButton = new JButton();
			translateButton.setText("Set Translation (x,y,z)");
			translateButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Visual v = worldObjects.get(patchList.getSelectedValue());
					if(v != null)
					{
						String[] coords = translateTextField.getText().split(",");
						v.setTranslation(new Vector3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
					}
				}
			});
		}
		return translateButton;
	}

	/**
	 * This method initializes translateTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTranslateTextField()
	{
		if(translateTextField == null)
		{
			translateTextField = new JTextField();
			translateTextField.setPreferredSize(new Dimension(100, 20));
			translateTextField.setText("0.0,0.0,0.0");
		}
		return translateTextField;
	}

	/**
	 * This method initializes newSceneItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewSceneItem()
	{
		if(newSceneItem == null)
		{
			newSceneItem = new JMenuItem();
			newSceneItem.setText("New Scene");
			newSceneItem.setMnemonic(KeyEvent.VK_N);
			newSceneItem.setVisible(true);
			newSceneItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					clearScene();
				}
			});
		}
		return newSceneItem;
	}

	/**
	 * Clears the scene of world objects, lights, materials, and resets the
	 * ambient light to default.
	 */
	private void clearScene()
	{
		patchListModel.clear();
		lightListModel.clear();
		materialListModel.clear();
		shaderListModel.clear();
		worldObjects.clear();
		lights.clear();
		materials.clear();
		shaders.clear();
		AmbientLight.getInstance().setIntensity(Color.darkGray);
	}

	/**
	 * This method initializes setAmbientLightButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSetAmbientLightButton()
	{
		if(setAmbientLightButton == null)
		{
			setAmbientLightButton = new JButton();
			setAmbientLightButton.setText("Set Ambient Light");
			setAmbientLightButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					AmbientLight light = AmbientLight.getInstance();
					Color c = JColorChooser.showDialog(null, "Choose Ambient Light Color", light.getIntensity());
					if(c != null)
						light.setIntensity(c);
				}
			});
		}
		return setAmbientLightButton;
	}

	/**
	 * This method initializes optionsMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getOptionsMenu()
	{
		if(optionsMenu == null)
		{
			optionsMenu = new JMenu();
			optionsMenu.setText("Options");
			optionsMenu.setMnemonic(KeyEvent.VK_P);
			optionsMenu.add(getEnableLightItem());
			optionsMenu.add(getSmoothShadingMenuItem());
		}
		return optionsMenu;
	}

	/**
	 * This method initializes enableLightItem
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getEnableLightItem()
	{
		if(enableLightItem == null)
		{
			enableLightItem = new JCheckBoxMenuItem();
			enableLightItem.setText("Lighting Enabled");
			enableLightItem.setSelected(true);
			enableLightItem.setMnemonic(KeyEvent.VK_L);
			enableLightItem.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
				}
			});
		}
		return enableLightItem;
	}

	/**
	 * This method initializes smoothShadingMenuItem
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getSmoothShadingMenuItem()
	{
		if(smoothShadingMenuItem == null)
		{
			smoothShadingMenuItem = new JCheckBoxMenuItem();
			smoothShadingMenuItem.setText("Smooth Shading Enabled");
			smoothShadingMenuItem.setSelected(true);
			smoothShadingMenuItem.setMnemonic(KeyEvent.VK_M);
			smoothShadingMenuItem.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
				}
			});
		}
		return smoothShadingMenuItem;
	}

	/**
	 * This method initializes addTeapotButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddTeapotButton()
	{
		if(addTeapotButton == null)
		{
			addTeapotButton = new JButton();
			addTeapotButton.setText("Add Teapot");
			addTeapotButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name = JOptionPane.showInputDialog("Enter a name for the Teapot", "");
					if(name != null)
					{
						Teapot t = new Teapot();
						t.setName(name);
						worldObjects.put(name, new Visual(t));
						patchListModel.addElement(name);
					}
				}
			});
		}
		return addTeapotButton;
	}

	/**
	 * This method initializes addDistantLightButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddDistantLightButton()
	{
		if(addDistantLightButton == null)
		{
			addDistantLightButton = new JButton();
			addDistantLightButton.setText("Add Distant Light");
			addDistantLightButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name = JOptionPane.showInputDialog("Enter a name for the Distant Light", "");
					if(name != null)
					{
						DistantLight l = new DistantLight();
						l.setName(name);
						lights.put(name, l);
						lightListModel.addElement(name);
						checkMaxLights();
					}
				}
			});
		}
		return addDistantLightButton;
	}

	/**
	 * This method initializes addMaterialButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddMaterialButton()
	{
		if(addMaterialButton == null)
		{
			addMaterialButton = new JButton();
			addMaterialButton.setText("Add New Material");
			addMaterialButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name = JOptionPane.showInputDialog("Enter a name for the Material", "");
					if(name != null)
					{
						Material m = new Material();
						m.setName(name);
						materials.put(name, m);
						materialListModel.addElement(name);
					}
				}
			});
		}
		return addMaterialButton;
	}

	/**
	 * This method initializes shininessSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getShininessSlider()
	{
		if(shininessSlider == null)
		{
			shininessSlider = new JSlider();
			shininessSlider.setMajorTickSpacing(32);
			shininessSlider.setMaximum(128);
			shininessSlider.setMinorTickSpacing(8);
			shininessSlider.setValue(64);
			shininessSlider.setSnapToTicks(false);
			shininessSlider.setPaintTicks(false);
			shininessSlider.setPaintLabels(true);
			shininessSlider.setPaintTrack(true);
			shininessSlider.setEnabled(false);
			shininessSlider.setExtent(0);
			shininessSlider.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					Material m = materials.get(materialList.getSelectedValue());
					m.setShininess(shininessSlider.getValue());
				}
			});
		}
		return shininessSlider;
	}

	/**
	 * This method initializes lightScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getLightScrollPane()
	{
		if(lightScrollPane == null)
		{
			lightScrollPane = new JScrollPane();
			lightScrollPane.setViewportView(getLightList());
		}
		return lightScrollPane;
	}

	/**
	 * This method initializes lightList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getLightList()
	{
		if(lightList == null)
		{
			lightList = new JList();
			lightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lightList.setModel(lightListModel);
			lightList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
			{
				public void valueChanged(javax.swing.event.ListSelectionEvent e)
				{
					if(lightList.getSelectedValue() == null)
						return;
					Light l = lights.get(lightList.getSelectedValue());
					if(l instanceof PositionalLight)
					{
						PositionalLight p = (PositionalLight) l;
						getEditConstantTextField().setText(Float.toString(p.getConstantAttenuation()));
						getEditLinearTextField().setText(Float.toString(p.getLinearAttenuation()));
						getEditQuadraticTextField().setText(Float.toString(p.getQuadraticAttenuation()));
						getEditLightPositionTextField().setText(p.getPosition().getX() + "," + p.getPosition().getY() + "," + p.getPosition().getZ());
						if(l instanceof SpotLight)
						{
							SpotLight s = (SpotLight) l;
							editLightCutoffSlider.setValue((int) s.getCutoffAngle());
							editLightExponentSlider.setValue((int) s.getExponent());
							getEditLightDirectionTextField().setText(s.getDirection().getX() + "," + s.getDirection().getY() + "," + s.getDirection().getZ());
						}
					}
					if(l instanceof DistantLight)
					{
						DistantLight d = (DistantLight) l;
						getEditLightDirectionTextField().setText(d.getDirection().getX() + "," + d.getDirection().getY() + "," + d.getDirection().getZ());
					}
					getEditLightAmbientButton().setEnabled(true);
					getEditLightDiffuseButton().setEnabled(true);
					getEditLightSpecularButton().setEnabled(true);
					getEditLightDirectionButton().setEnabled(l instanceof DistantLight || l instanceof SpotLight);
					getEditLightDirectionTextField().setEnabled(l instanceof DistantLight || l instanceof SpotLight);
					getEditLightLocationButton().setEnabled(l instanceof PositionalLight);
					getEditLightPositionTextField().setEnabled(l instanceof PositionalLight);
					getEditConstantButton().setEnabled(l instanceof PositionalLight);
					getEditLinearButton().setEnabled(l instanceof PositionalLight);
					getEditQuadraticButton().setEnabled(l instanceof PositionalLight);
					getEditConstantTextField().setEnabled(l instanceof PositionalLight);
					getEditLinearTextField().setEnabled(l instanceof PositionalLight);
					getEditQuadraticTextField().setEnabled(l instanceof PositionalLight);
					getEditLightCutoffSlider().setEnabled(l instanceof SpotLight);
					getEditLightExponentSlider().setEnabled(l instanceof SpotLight);
					editLightCutoffLabel.setEnabled(l instanceof SpotLight);
					editExponentLabel.setEnabled(l instanceof SpotLight);
				}
			});
		}
		return lightList;
	}

	/**
	 * This method initializes materialScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getMaterialScrollPane()
	{
		if(materialScrollPane == null)
		{
			materialScrollPane = new JScrollPane();
			materialScrollPane.setViewportView(getMaterialList());
		}
		return materialScrollPane;
	}

	/**
	 * This method initializes materialList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getMaterialList()
	{
		if(materialList == null)
		{
			materialList = new JList();
			materialList.setModel(materialListModel);
			materialList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
			{
				public void valueChanged(javax.swing.event.ListSelectionEvent e)
				{
					if(materialList.getSelectedValue() == null)
						return;
					Material m = materials.get(materialList.getSelectedValue());
					getSetAmbientButton().setEnabled(true);
					getSetDiffuseButton().setEnabled(true);
					getSetSpecularButton().setEnabled(true);
					getSetEmissionButton().setEnabled(true);
					shininessLabel.setEnabled(true);
					getShininessSlider().setValue((int) m.getShininess());
					getShininessSlider().setEnabled(true);
				}
			});
		}
		return materialList;
	}

	/**
	 * This method initializes setAmbientButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSetAmbientButton()
	{
		if(setAmbientButton == null)
		{
			setAmbientButton = new JButton();
			setAmbientButton.setText("Set Ambient Color");
			setAmbientButton.setEnabled(false);
			setAmbientButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Material m = materials.get(materialList.getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Ambient Color", m.getAmbient());
					if(c != null)
						m.setAmbient(c);
				}
			});
		}
		return setAmbientButton;
	}

	/**
	 * This method initializes setDiffuseButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSetDiffuseButton()
	{
		if(setDiffuseButton == null)
		{
			setDiffuseButton = new JButton();
			setDiffuseButton.setText("Set Diffuse Color");
			setDiffuseButton.setEnabled(false);
			setDiffuseButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Material m = materials.get(materialList.getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Diffuse Color", m.getDiffuse());
					if(c != null)
						m.setDiffuse(c);
				}
			});
		}
		return setDiffuseButton;
	}

	/**
	 * This method initializes setSpecularButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSetSpecularButton()
	{
		if(setSpecularButton == null)
		{
			setSpecularButton = new JButton();
			setSpecularButton.setText("Set Specular Color");
			setSpecularButton.setEnabled(false);
			setSpecularButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Material m = materials.get(materialList.getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Specular Color", m.getSpecular());
					if(c != null)
						m.setSpecular(c);
				}
			});
		}
		return setSpecularButton;
	}

	/**
	 * This method initializes setEmissionButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSetEmissionButton()
	{
		if(setEmissionButton == null)
		{
			setEmissionButton = new JButton();
			setEmissionButton.setText("Set Emission Color");
			setEmissionButton.setEnabled(false);
			setEmissionButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Material m = materials.get(materialList.getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Emission Color", m.getEmission());
					if(c != null)
						m.setEmission(c);
				}
			});
		}
		return setEmissionButton;
	}

	/**
	 * This method initializes setModelMaterialScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSetModelMaterialScrollPane()
	{
		if(setModelMaterialScrollPane == null)
		{
			setModelMaterialScrollPane = new JScrollPane();
			setModelMaterialScrollPane.setViewportView(getSetModelMaterialList());
		}
		return setModelMaterialScrollPane;
	}

	/**
	 * This method initializes setModelMaterialList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getSetModelMaterialList()
	{
		if(setModelMaterialList == null)
		{
			setModelMaterialList = new JList();
			setModelMaterialList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setModelMaterialList.setModel(materialListModel);
			setModelMaterialList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
			{
				public void valueChanged(javax.swing.event.ListSelectionEvent e)
				{
					if(getPatchList().getSelectedValue() == null || getSetModelMaterialList().getSelectedValue() == null)
						return;
					Visual v = worldObjects.get(getPatchList().getSelectedValue());
					Material m = materials.get(getSetModelMaterialList().getSelectedValue());
					v.setMaterial(m);
				}
			});
		}
		return setModelMaterialList;
	}

	/**
	 * This method initializes addPositionalLightButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddPositionalLightButton()
	{
		if(addPositionalLightButton == null)
		{
			addPositionalLightButton = new JButton();
			addPositionalLightButton.setText("Add Positional Light");
			addPositionalLightButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name = JOptionPane.showInputDialog("Enter a name for the Positional Light", "");
					if(name != null)
					{
						PositionalLight l = new PositionalLight();
						l.setName(name);
						lights.put(name, l);
						lightListModel.addElement(name);
						checkMaxLights();
					}
				}
			});
		}
		return addPositionalLightButton;
	}

	private void checkMaxLights()
	{
		boolean enabled = lights.size() < 8;
		getAddDistantLightButton().setEnabled(enabled);
		getAddPositionalLightButton().setEnabled(enabled);
		getAddSpotLightButton().setEnabled(enabled);
	}

	/**
	 * This method initializes addSpotLightButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddSpotLightButton()
	{
		if(addSpotLightButton == null)
		{
			addSpotLightButton = new JButton();
			addSpotLightButton.setText("Add Spot Light");
			addSpotLightButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name = JOptionPane.showInputDialog("Enter a name for the Spot Light", "");
					if(name != null)
					{
						SpotLight l = new SpotLight();
						l.setName(name);
						lights.put(name, l);
						lightListModel.addElement(name);
						checkMaxLights();
					}
				}
			});
		}
		return addSpotLightButton;
	}

	/**
	 * This method initializes editLightAmbientButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditLightAmbientButton()
	{
		if(editLightAmbientButton == null)
		{
			editLightAmbientButton = new JButton();
			editLightAmbientButton.setText("Edit Ambient Color");
			editLightAmbientButton.setActionCommand("Edit Ambient Color");
			editLightAmbientButton.setEnabled(false);
			editLightAmbientButton.setVisible(true);
			editLightAmbientButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Light l = lights.get(getLightList().getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Ambient Light Color", l.getAmbient());
					if(c != null)
						l.setAmbient(c);
				}
			});
		}
		return editLightAmbientButton;
	}

	/**
	 * This method initializes editLightDiffuseButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditLightDiffuseButton()
	{
		if(editLightDiffuseButton == null)
		{
			editLightDiffuseButton = new JButton();
			editLightDiffuseButton.setText("Edit Diffuse Color");
			editLightDiffuseButton.setActionCommand("Edit Diffuse Color");
			editLightDiffuseButton.setEnabled(false);
			editLightDiffuseButton.setVisible(true);
			editLightDiffuseButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Light l = lights.get(getLightList().getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Diffuse Light Color", l.getDiffuse());
					if(c != null)
						l.setDiffuse(c);
				}
			});
		}
		return editLightDiffuseButton;
	}

	/**
	 * This method initializes editLightSpecularButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditLightSpecularButton()
	{
		if(editLightSpecularButton == null)
		{
			editLightSpecularButton = new JButton();
			editLightSpecularButton.setText("Edit Specular Color");
			editLightSpecularButton.setActionCommand("Edit Specular Color");
			editLightSpecularButton.setEnabled(false);
			editLightSpecularButton.setVisible(true);
			editLightSpecularButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Light l = lights.get(getLightList().getSelectedValue());
					Color c = JColorChooser.showDialog(null, "Choose Specular Light Color", l.getSpecular());
					if(c != null)
						l.setSpecular(c);
				}
			});
		}
		return editLightSpecularButton;
	}

	/**
	 * This method initializes editLightCutoffSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getEditLightCutoffSlider()
	{
		if(editLightCutoffSlider == null)
		{
			editLightCutoffSlider = new JSlider();
			editLightCutoffSlider.setMaximum(90);
			editLightCutoffSlider.setMajorTickSpacing(30);
			editLightCutoffSlider.setValue(45);
			editLightCutoffSlider.setPaintTicks(false);
			editLightCutoffSlider.setPaintLabels(true);
			editLightCutoffSlider.setMinorTickSpacing(25);
			editLightCutoffSlider.setEnabled(false);
			editLightCutoffSlider.setVisible(true);
			editLightCutoffSlider.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					SpotLight l = (SpotLight) lights.get(getLightList().getSelectedValue());
					l.setCutoffAngle(editLightCutoffSlider.getValue());
				}
			});
		}
		return editLightCutoffSlider;
	}

	/**
	 * This method initializes editLightExponentSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getEditLightExponentSlider()
	{
		if(editLightExponentSlider == null)
		{
			editLightExponentSlider = new JSlider();
			editLightExponentSlider.setMajorTickSpacing(32);
			editLightExponentSlider.setPaintLabels(true);
			editLightExponentSlider.setMinorTickSpacing(8);
			editLightExponentSlider.setEnabled(false);
			editLightExponentSlider.setMaximum(128);
			editLightExponentSlider.setValue(64);
			editLightExponentSlider.setVisible(true);
			editLightExponentSlider.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					SpotLight l = (SpotLight) lights.get(getLightList().getSelectedValue());
					l.setExponent(editLightExponentSlider.getValue());
				}
			});
		}
		return editLightExponentSlider;
	}

	/**
	 * This method initializes editLightDirectionButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditLightDirectionButton()
	{
		if(editLightDirectionButton == null)
		{
			editLightDirectionButton = new JButton();
			editLightDirectionButton.setText("Set Direction (x,y,z)");
			editLightDirectionButton.setEnabled(false);
			editLightDirectionButton.setVisible(true);
			editLightDirectionButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Light l = lights.get(getLightList().getSelectedValue());
					String[] coords = getEditLightDirectionTextField().getText().split(",");
					if(l instanceof SpotLight)
					{
						SpotLight s = (SpotLight) l;
						s.setDirection(new Vector3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
					}
					else if(l instanceof DistantLight)
					{
						DistantLight d = (DistantLight) l;
						d.setDirection(new Vector3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
					}
				}
			});
		}
		return editLightDirectionButton;
	}

	/**
	 * This method initializes editLightLocationButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditLightLocationButton()
	{
		if(editLightLocationButton == null)
		{
			editLightLocationButton = new JButton();
			editLightLocationButton.setText("Set Position (x,y,z)");
			editLightLocationButton.setEnabled(false);
			editLightLocationButton.setVisible(true);
			editLightLocationButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					PositionalLight p = (PositionalLight) lights.get(getLightList().getSelectedValue());
					String[] coords = getEditLightPositionTextField().getText().split(",");
					p.setPosition(new Point3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
				}
			});
		}
		return editLightLocationButton;
	}

	/**
	 * This method initializes editLinearButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditLinearButton()
	{
		if(editLinearButton == null)
		{
			editLinearButton = new JButton();
			editLinearButton.setText("Set Linear Attenuation");
			editLinearButton.setEnabled(false);
			editLinearButton.setVisible(true);
			editLinearButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					PositionalLight p = (PositionalLight) lights.get(getLightList().getSelectedValue());
					p.setLinearAttenuation(Float.parseFloat(getEditLinearTextField().getText()));
				}
			});
		}
		return editLinearButton;
	}

	/**
	 * This method initializes editConstantButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditConstantButton()
	{
		if(editConstantButton == null)
		{
			editConstantButton = new JButton();
			editConstantButton.setText("Set Constant Attenuation");
			editConstantButton.setEnabled(false);
			editConstantButton.setVisible(true);
			editConstantButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					PositionalLight p = (PositionalLight) lights.get(getLightList().getSelectedValue());
					p.setConstantAttenuation(Float.parseFloat(getEditConstantTextField().getText()));
				}
			});
		}
		return editConstantButton;
	}

	/**
	 * This method initializes editQuadraticButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEditQuadraticButton()
	{
		if(editQuadraticButton == null)
		{
			editQuadraticButton = new JButton();
			editQuadraticButton.setText("Set Quadratic Attenuation");
			editQuadraticButton.setEnabled(false);
			editQuadraticButton.setVisible(true);
			editQuadraticButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					PositionalLight p = (PositionalLight) lights.get(getLightList().getSelectedValue());
					p.setQuadraticAttenuation(Float.parseFloat(getEditQuadraticTextField().getText()));
				}
			});
		}
		return editQuadraticButton;
	}

	/**
	 * This method initializes editLightDirectionTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEditLightDirectionTextField()
	{
		if(editLightDirectionTextField == null)
		{
			editLightDirectionTextField = new JTextField();
			editLightDirectionTextField.setEnabled(false);
			editLightDirectionTextField.setPreferredSize(new Dimension(100, 20));
		}
		return editLightDirectionTextField;
	}

	/**
	 * This method initializes editLightPositionTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEditLightPositionTextField()
	{
		if(editLightPositionTextField == null)
		{
			editLightPositionTextField = new JTextField();
			editLightPositionTextField.setEnabled(false);
			editLightPositionTextField.setPreferredSize(new Dimension(100, 20));
		}
		return editLightPositionTextField;
	}

	/**
	 * This method initializes editConstantTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEditConstantTextField()
	{
		if(editConstantTextField == null)
		{
			editConstantTextField = new JTextField();
			editConstantTextField.setEnabled(false);
		}
		return editConstantTextField;
	}

	/**
	 * This method initializes editLinearTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEditLinearTextField()
	{
		if(editLinearTextField == null)
		{
			editLinearTextField = new JTextField();
			editLinearTextField.setEnabled(false);
		}
		return editLinearTextField;
	}

	/**
	 * This method initializes editQuadraticTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEditQuadraticTextField()
	{
		if(editQuadraticTextField == null)
		{
			editQuadraticTextField = new JTextField();
			editQuadraticTextField.setEnabled(false);
		}
		return editQuadraticTextField;
	}

	/**
	 * This method initializes shaderTab
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getShaderTab()
	{
		if(shaderTab == null)
		{
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints65.gridy = 5;
			gridBagConstraints65.weightx = 1.0;
			gridBagConstraints65.weighty = 1.0;
			gridBagConstraints65.anchor = GridBagConstraints.NORTH;
			gridBagConstraints65.gridx = 0;
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.gridx = 0;
			gridBagConstraints64.gridy = 4;
			selectShaderLabel = new JLabel();
			selectShaderLabel.setText("Select A Shader:");
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints63.gridy = 3;
			gridBagConstraints63.weightx = 0.0;
			gridBagConstraints63.weighty = 0.0;
			gridBagConstraints63.gridx = 0;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridx = 0;
			gridBagConstraints62.gridy = 2;
			selectShaderModelLabel = new JLabel();
			selectShaderModelLabel.setText("Select A Model:");
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 0;
			gridBagConstraints61.gridy = 1;
			shaderTab = new JPanel();
			shaderTab.setLayout(new GridBagLayout());
			shaderTab.add(getNewShaderButton(), gridBagConstraints61);
			shaderTab.add(selectShaderModelLabel, gridBagConstraints62);
			shaderTab.add(getSelectShaderModelScrollPane(), gridBagConstraints63);
			shaderTab.add(selectShaderLabel, gridBagConstraints64);
			shaderTab.add(getSelectShaderScrollPane(), gridBagConstraints65);
		}
		return shaderTab;
	}

	/**
	 * This method initializes newShaderButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNewShaderButton()
	{
		if(newShaderButton == null)
		{
			newShaderButton = new JButton();
			newShaderButton.setText("New Shader");
			newShaderButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name = JOptionPane.showInputDialog("Enter A Name for the Shader", "MyShader");
					if (name != null)
					{
						JFileChooser fc = new JFileChooser();
						int returnVal = fc.showDialog(null, "Open Vertex Shader");
						if(returnVal == JFileChooser.APPROVE_OPTION)
						{
							String vertexFilePath = fc.getSelectedFile().getAbsolutePath();
							returnVal = fc.showDialog(null, "Open Fragment Shader");
							if(returnVal == JFileChooser.APPROVE_OPTION)
							{
								String fragmentFilePath = fc.getSelectedFile().getAbsolutePath();
								Shader s = new Shader();
								s.setName(name);
								String[] vertexSource = graphicslib3D.GLSLUtils.readShaderSource(vertexFilePath);
								String[] fragmentSource = graphicslib3D.GLSLUtils.readShaderSource(fragmentFilePath);
								s.loadFromSource(vertexSource, fragmentSource);
								s.setParameterName(JOptionPane.showInputDialog("Enter an optional (uniform float) parameter name"));
								returnVal = fc.showDialog(null, "Open Optional Texture File");
								if(returnVal == JFileChooser.APPROVE_OPTION)
								{
									Texture t = new Texture();
									try
									{
										t.loadFromFile(fc.getSelectedFile().getAbsolutePath());
										s.setTexture(t);
									}
									catch(Exception e1)
									{
										e1.printStackTrace();
									}
								}
								shaders.put(name, s);
								shaderListModel.addElement(name);
							}
						}
					}
				}
			});
		}
		return newShaderButton;
	}

	/**
	 * This method initializes selectShaderModelScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSelectShaderModelScrollPane()
	{
		if(selectShaderModelScrollPane == null)
		{
			selectShaderModelScrollPane = new JScrollPane();
			selectShaderModelScrollPane.setViewportView(getSelectShaderModelList());
		}
		return selectShaderModelScrollPane;
	}

	/**
	 * This method initializes selectShaderModelList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getSelectShaderModelList()
	{
		if(selectShaderModelList == null)
		{
			selectShaderModelList = new JList();
			selectShaderModelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			selectShaderModelList.setModel(patchListModel);
			selectShaderModelList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
			{
				public void valueChanged(javax.swing.event.ListSelectionEvent e)
				{
					if(selectShaderModelList.getSelectedValue() == null)
						return;
					getSelectShaderList().setEnabled(true);
					Visual v = worldObjects.get(selectShaderModelList.getSelectedValue());
					if (v.getShader() == null)
						getSelectShaderList().clearSelection();
					else
						getSelectShaderList().setSelectedValue(v.getShader().getName(), true);
				}
			});
		}
		return selectShaderModelList;
	}

	/**
	 * This method initializes selectShaderScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSelectShaderScrollPane()
	{
		if(selectShaderScrollPane == null)
		{
			selectShaderScrollPane = new JScrollPane();
			selectShaderScrollPane.setViewportView(getSelectShaderList());
		}
		return selectShaderScrollPane;
	}

	/**
	 * This method initializes selectShaderList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getSelectShaderList()
	{
		if(selectShaderList == null)
		{
			selectShaderList = new JList();
			selectShaderList.setEnabled(false);
			selectShaderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			selectShaderList.setModel(shaderListModel);
			selectShaderList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
			{
				public void valueChanged(javax.swing.event.ListSelectionEvent e)
				{
					if(getSelectShaderModelList().getSelectedValue() == null || getSelectShaderList().getSelectedValue() == null)
						return;
					Visual v = worldObjects.get(getSelectShaderModelList().getSelectedValue());
					Shader s = shaders.get(getSelectShaderList().getSelectedValue());
					v.setShader(s);
				}
			});
		}
		return selectShaderList;
	}
}
