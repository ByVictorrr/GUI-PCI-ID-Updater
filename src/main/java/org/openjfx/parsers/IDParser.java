package org.openjfx.parsers;




import org.openjfx.models.Device;
import org.openjfx.models.PCI_ID;
import org.openjfx.utilities.FTPInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class IDParser {
    public static final int VEN =0;
    public static final int DEV =1;
    public static final int SVEN =2;
    public static final int SDEV =3;

    public enum DeviceLineNumber{
        SET, NO_DEV, NO_VEN, EXISTS
    }

    static public List<Long> getExcelIDS(String unparsed) {
        String []ids = unparsed.replaceAll("(h|0x)","").split(" ");
        List<Long> pIds = new ArrayList<>();
        try {
            for (String id : ids) {
                pIds.add(Long.parseLong(id, 16));
            }
            return pIds;
        }catch (Exception e){
            return null;
        }
    }

    static public Long getDatabaseID(Integer type, String line) {
        Long id = null;
        try {
            switch (type) {
                case VEN:
                    id = Long.parseLong(line.substring(0, 4), 16);
                    break;
                case DEV:
                    id = Long.parseLong(line.substring(1, 5), 16);
                    break;
                case SVEN:
                    id = Long.parseLong(line.substring(2, 6), 16);
                    break;
                case SDEV:
                    id = Long.parseLong(line.substring(7, 11), 16);
                    break;
                default:
                    System.out.println("Wrong type; No such found");
            }
        }catch (Exception e){
            return null;
        }
        return id;
    }
    /**
     * @parm unset - a device that has an unset line number
     * @parm in - input stream of the raw pci.ids file used to help find device number
     * @return - a
     */
    public static DeviceLineNumber setLineNumber(Device unset, FTPInputStream in)throws IOException {
        long lineNum=1;
        String line;
        long numTabs=0;
        Long id, id1;
        PCI_ID lineID = new PCI_ID();
        PCI_ID currID = unset.getIds();
        in.getInputStream().reset();
        BufferedReader reader = in.getReader();
        while((line=reader.readLine()) != null){
            // Case 1 - comment (skip)
            if(line.equals("") || line.charAt(0) == '#'){
                lineNum++;
                continue;
            }
            // case 2 - count the number of tabs
            if((numTabs=countTabs(line)) == 0){
                // VENDOR ID
                id = getDatabaseID(IDParser.VEN, line);
                if(lineID.getVen_id().equals(currID.getVen_id()) && !id.equals(lineID.getVen_id()))
                    return DeviceLineNumber.NO_DEV;
                else if(id != null && id.equals(currID.getVen_id()))
                    lineID.setVen_id(id);
            }else if(numTabs == 1) {
                // DEVICE ID
                id = getDatabaseID(IDParser.DEV, line);
                if(lineID.getVen_id().equals(currID.getVen_id()) && id != null && id.equals(currID.getDev_id()))
                    lineID.setDev_id(id);
                else if(lineID.getVen_id().equals(currID.getVen_id())  && lineID.getDev_id().equals(currID.getDev_id())){
                    unset.setLineNum(lineNum);
                    return DeviceLineNumber.SET;
                }
            }else{
                // sub-system
                id = getDatabaseID(IDParser.SVEN, line);
                id1 = getDatabaseID(IDParser.SDEV, line);
                // Check to see if the entry exists
                if(lineID.getVen_id().equals(currID.getVen_id()) && lineID.getDev_id().equals(currID.getDev_id()) &&
                        id!=null &&  id1!= null
                ){
                    if (id.equals(currID.getSven_id()) && id1.equals(currID.getSdev_id())) {
                        System.out.println("Device " + currID.toString() + " Already Exists");
                        return DeviceLineNumber.EXISTS;

                    }
                    else if ((id.longValue() > currID.getSven_id().longValue()) ||
                            (id.equals(currID.getSven_id()) && id1.longValue() > currID.getSdev_id().longValue())){
                        unset.setLineNum(lineNum);
                        return DeviceLineNumber.SET;

                    }
                }

            }
            lineNum++;
        }

        return DeviceLineNumber.NO_VEN;
    }
    private static long countTabs(String s){
        return s.chars().filter(c->c=='\t').count();
    }

}
