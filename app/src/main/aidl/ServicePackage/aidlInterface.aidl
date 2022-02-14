// aidlInterface.aidl
package ServicePackage;

// Declare any non-default types here with import statements

interface aidlInterface {


    int menuClick(String id ,int value);

    String vehicleModel();

    void updateValues(String id, int value);

    void updateControl(String column, int value);

    int getTarget();

    int getValue(String menu);

    int getDisplay();

    void updateDisplay(int value);

}