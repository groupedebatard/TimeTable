/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package timeTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of TimeTableDB.
 * 
 */


public class TimeTableDB {
	protected static org.jdom2.Document file;
	protected String fileS;
	public static HashSet<TimeTable> TTSet;
	public static HashSet<TeacherTT> TTTSet;
	public static HashSet<Room> RoomsSet;

	public TimeTableDB(String file) {
		// Start of user code constructor for TimeTableDB)
		TimeTableDB.TTSet = new HashSet<TimeTable>();
		TimeTableDB.TTTSet = new HashSet<TeacherTT>();
		TimeTableDB.RoomsSet = new HashSet<Room>();

		// End of user code
	}

	/**
	 * Description of the method saveDB.
	 */
	public static void showDB() {
		try{
			
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(file, System.out);}
		catch (java.io.IOException e){}
	}
	
	public void saveDB() {
		
		try{
						XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(file, new FileOutputStream("timeTableDB.xml"));}
		catch (java.io.IOException e){}
	}
	
	
	
	
	
	
	public String getfile() {
		return fileS;
	}
	
	public void loadDB(){
		SAXBuilder sxb = new SAXBuilder();
		fileS = this.getFile();
		try {
			file = sxb.build(new File(fileS));}
		catch(Exception e){}
		org.jdom2.Element TTDBElt = file.getRootElement();
		org.jdom2.Element RoomsElt = TTDBElt.getChild("Rooms");
		org.jdom2.Element TTsElt = TTDBElt.getChild("TimeTables");
		List<org.jdom2.Element> RoomElt = (RoomsElt).getChildren("Room");
		List<org.jdom2.Element> TTElt = (TTsElt).getChildren("TimeTable");
		Iterator<org.jdom2.Element> ItRooms = RoomElt.iterator();
		Iterator<org.jdom2.Element> ItTT = TTElt.iterator();
		while(ItRooms.hasNext()){
			org.jdom2.Element Room = (org.jdom2.Element)ItRooms.next();
			Integer RoomId = Integer.parseInt(((org.jdom2.Element) Room).getChild("RoomId").getText());
			Integer capacity = Integer.parseInt(((org.jdom2.Element) Room).getChild("Capacity").getText());
			Room NRoom = new Room (RoomId, capacity);
			TimeTableDB.RoomsSet.add(NRoom);
			}
			while(ItTT.hasNext()){
				org.jdom2.Element TT = (org.jdom2.Element)ItTT.next();
				Integer GroupId = Integer.parseInt(((org.jdom2.Element) TT).getChild("GroupId").getText());
				List<org.jdom2.Element> BooksElts = ((org.jdom2.Element) TT).getChildren("Books");
				Iterator<org.jdom2.Element> ItBooks = BooksElts.iterator();
				TimeTable TTI = new TimeTable (GroupId);
				while(ItBooks.hasNext()){
					org.jdom2.Element Books = (org.jdom2.Element)ItTT.next();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mi:ss");
					Integer BookingId = Integer.parseInt(((org.jdom2.Element) Books).getChild("BookingId").getText());
					String Login = ((org.jdom2.Element) Books).getChild("Login").getText();
					
					Date DateEnd = null, DateBegin = null;
					try {
						DateBegin = sdf.parse(((org.jdom2.Element) Books).getChild("DateBegin").getText());
						DateEnd = sdf.parse(((org.jdom2.Element) Books).getChild("DateEnd").getText());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Integer RoomId = Integer.parseInt(((org.jdom2.Element) Books).getChild("RoomId").getText());
					Booking Book = new Booking (BookingId, Login, DateBegin, DateEnd, RoomId);
					TTI.addBooking(Book);
				}
				TimeTableDB.TTSet.add(TTI);
			}
				
			}	
	
	
	

	/**
	 * Description of the method CheckDB.
	 */
	public void CheckDB() {
		// Start of user code for method CheckDB
		// End of user code
	}

	// Start of user code (user defined methods for TimeTableDB)

	// End of user code
	/**
	 * Returns timeTables.
	 * @return timeTables 
	 */
	public HashSet<TimeTable> getTimeTables() {
		return TimeTableDB.TTSet;
	}

	public HashSet<TeacherTT> getTeacherTTs() {
		return TimeTableDB.TTTSet;
	}

	public String getFile() {
		return fileS;
	}

	public void setFile(String fileS) {
		this.fileS = fileS;
	}

	public HashSet<Room> getRooms() {
		return TimeTableDB.RoomsSet;
	}
	}