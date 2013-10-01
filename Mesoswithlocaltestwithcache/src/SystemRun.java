
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;




/*
 * This Class provide by the System, 
 */

public class SystemRun 
{
	
	public static List<File> GetaAllInputs(String inputdir)
	{
		
		 String path=inputdir;
		 File inputfiles = new File(path);
	     String[] filename = inputfiles.list();
	     File[] files= inputfiles.listFiles();
		 
	     List<File> allinputfiles = new ArrayList<File>();
	        
	        
	     for(int i = 0; i < files.length; ++i)
			{
				allinputfiles.add(files[i]); 
			}     
		return allinputfiles;
	}
	
	
	  public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
	        
	        int listSize = list.size();                                                          
	        int page = (listSize + (pageSize-1))/ pageSize;                     
	        
	        List<List<T>> listArray = new ArrayList<List<T>>();         
	        for(int i=0;i<page;i++) {                                                  
	            List<T> subList = new ArrayList<T>();                               
	            for(int j=0;j<listSize;j++) {                                                 
	                int pageIndex = ( (j + 1) + (pageSize-1) ) / pageSize;   
	                if(pageIndex == (i + 1)) {                                               
	                    subList.add(list.get(j));  
	                }
	                
	                if( (j + 1) == ((i + 1) * pageSize) ) {                               
	                    break;
	                }
	            }
	            listArray.add(subList);                                                         
	        }
	        return listArray;
	    }
	
	
	public static List<Object> GenerateTasks(List<File> lista, List<File> listb){
		List<Object> taskinput = new ArrayList<Object>();
		int lengtha,lengthb;
		lengtha=lista.size();
		lengthb=listb.size();
		
		for(int i=0;i<lengtha;i++)
			taskinput.add(lista.get(i));
		for(int j=0;j<lengthb;j++)
			taskinput.add(listb.get(j));
		taskinput.add(lengtha);
		taskinput.add(lengthb);
		
		return taskinput;
	}
	
	
	public <T> int calculatetotalTasks(List<List<T>> listArray){
		return listArray.size()+listArray.size()*(listArray.size()-1)/2;
	}
	
	public static HashMap<Integer,List<Object>> GenerateTasksTable(List<List<File>> listArray)
	{
		int z=0;
		HashMap<Integer,List<Object>> TasksTable = new HashMap<Integer,List<Object>>();
		for(int i=0;i<listArray.size();i++)
			for(int j=i;j<listArray.size();j++)
			{
				TasksTable.put(z,GenerateTasks(listArray.get(i),listArray.get(j)));
				z++;
			}
		return TasksTable;
	}
	
	public static List<File> getlista(List<Object> tasklist)
	{
		List<File> lista = new ArrayList<File>();
		int length,lengtha;
		length = tasklist.size();
		lengtha = (int) tasklist.get(length-2);
		for(int i=0;i<lengtha;i++)
			lista.add((File)tasklist.get(i));
		return lista;
	}
	
	public static List<File> getlistb(List<Object> tasklist)
	{
		List<File> listb = new ArrayList<File>();
		int length,lengtha,lengthb;
		length = tasklist.size();
		lengtha = (int) tasklist.get(length-2);
		lengthb = (int) tasklist.get(length-1);
		for(int j=0;j<lengthb;j++)
			listb.add((File) tasklist.get(j+lengtha));
		return listb;
	}
	
	
	
	
	  public static void copyFile2(File srcFile, String destDir, String newFileName) {   
	        if (!srcFile.exists()) {  
	            System.out.println("");    
	        } else if (newFileName == null) {  
	            System.out.println("");   
	        } else {  
	            try {  
	                FileChannel fcin = new FileInputStream(srcFile).getChannel();  
	                FileChannel fcout = new FileOutputStream(new File(destDir,newFileName)).getChannel();  
	                fcin.transferTo(0, fcin.size(), fcout);  
	                fcin.close();  
	                fcout.close();  
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    } 
	
	
	public static void getfilestolocal(List<File> listfilesa,List<File> listfilesb) throws IOException
	{
		String localdir = "c:\\try\\";
		String cache = "c:\\cache\\";
		List<String> delfilelist = new ArrayList<String>();
		List<String> copyfilenamelist = new ArrayList<String>();
		
		File f = new File(cache);
		File[] files= f.listFiles();
		String[] filename = f.list();
		List<String> filenamelist = java.util.Arrays.asList(filename);
		
		for(int i=0;i<filenamelist.size();i++)
			copyfilenamelist.add(filenamelist.get(i));
		
		
	    if(filename.length==0)
	    {
	    	System.out.println("isEmpty!");
	    	for(int i = 0; i < listfilesa.size(); ++i)
		{
	    	copyFile2(listfilesa.get(i),cache,listfilesa.get(i).getName());
		}
	    	if (!(listfilesa.equals(listfilesb)))
	    	{
	    		for(int j = 0; j < listfilesb.size(); ++j)
	    			{
	    			copyFile2(listfilesb.get(j),cache,listfilesb.get(j).getName());
		            }
	    	}
	    }
		else
		{
			for(int i = 0; i < listfilesa.size(); ++i)
			{
				if (filenamelist.contains(listfilesa.get(i).getName()))
					delfilelist.add(listfilesa.get(i).getName());
				else
					copyFile2(listfilesa.get(i),cache,listfilesa.get(i).getName());
			}
			
			if (!(listfilesa.equals(listfilesb)))
			{
				for(int j = 0; j < listfilesb.size(); ++j)
				{
					if (filenamelist.contains(listfilesb.get(j).getName()))
						delfilelist.add(listfilesb.get(j).getName());
					else
						copyFile2(listfilesb.get(j),cache,listfilesb.get(j).getName());
			    }	
			}
			
				
		}
	    
	    copyfilenamelist.removeAll(delfilelist);
	    for(int i=0;i<copyfilenamelist.size();i++)
			{
				File file = new File(cache,copyfilenamelist.get(i));
				file.delete();
			}
	    
	}
	
	
	
	public static HashMap<Integer,List<Object>> preparetorun(String hdfsinputdir){
		List<File> inputfiles = new ArrayList<File>();
		List<List<File>> splitedlist = new ArrayList<List<File>>();
		HashMap<Integer,List<Object>> taskmap = new HashMap<Integer,List<Object>>();
		
		inputfiles=GetaAllInputs(hdfsinputdir);
		splitedlist = splitList(inputfiles,2);
		taskmap = GenerateTasksTable(splitedlist);
	
		return taskmap;
	}
	
	public static void run(int taskid) 
	{
		String projectclassname;
		List<Object> tasklist = new ArrayList<Object>();
		List<File> lista = new ArrayList<File>();
		List<File> listb = new ArrayList<File>();
		int TaskID;
//		HashMap<Integer,List<Object>> taskmap;
		String allfilepath = "c:\\try\\";
		
		try 
		{
	        projectclassname = "Cvtreesystem";
	    	Class<?> Project = Class.forName(projectclassname);	    	
	    	Constructor<?> localConstructor = Project.getConstructor(new Class[0]);
	    	ComputingSystem<?, ?, ?, ?> realproject = (ComputingSystem<?, ?, ?, ?>) localConstructor.newInstance(new Object[0]);
	    	
//	    	taskmap = preparetorun(allfilepath);
	    	TaskID = taskid;
	    	tasklist = taskmap.get(TaskID);
	    	
	    	
	    	lista = getlista(tasklist);
	    	listb = getlistb(tasklist);
	    	
	    	
	    	
	    	realproject.Run(lista,listb,TaskID);
				
	    } catch (InstantiationException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e) {
		    e.printStackTrace();
        }
    }
	public static void main(String[] args)
	{
		int tasknumber;
		String allfilepath = "c:\\try\\";
		taskmap = new HashMap<Integer,List<Object>>();
		taskmap = preparetorun(allfilepath);
		tasknumber = taskmap.size();
		for(int i =0;i<tasknumber;i++) 
		run(i);
	}
	
	static HashMap<Integer,List<Object>> taskmap;
}