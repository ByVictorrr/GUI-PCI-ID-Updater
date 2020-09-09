package org.openjfx.adapters;

public class ConnectionAdapter {
    // Step 1 - get env vars
    public final static String PCI_EXCEL_URL=System.getenv("PCI_EXCEL_URL");
    public final static String PCI_EXCEL_SHEET=System.getenv("PCI_EXCEL_SHEET");
    public final static String PCI_HOST=System.getenv("PCI_HOST");
    public final static String PCI_ROOT_DIR=System.getenv("PCI_ROOT_DIR");
    public final static String PCI_USER=System.getenv("PCI_USER");
    public final static String PCI_PASS=System.getenv("PCI_PASS");


}
