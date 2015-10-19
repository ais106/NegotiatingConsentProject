package uk.ac.soton.ecs.negotiatingconsent.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.R;
import uk.ac.soton.ecs.negotiatingconsent.collectables.PhoneInfo;

/**
 * Created by anna on 08/09/15.
 */
public class PhoneStateAdapter extends ArrayAdapter<PhoneInfo> {

    Context context;
    int layoutResourceId;
    List<PhoneInfo> data = null;


    public PhoneStateAdapter(Context context, int layoutResourceId, List<PhoneInfo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PhoneStateHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new PhoneStateHolder();
            holder.deviceid = (TextView)row.findViewById(R.id.deviceId);
            holder.phonenumber = (TextView)row.findViewById(R.id.phone);
            holder.phonelocation = (TextView)row.findViewById(R.id.phonelocation);
            holder.phonetype = (TextView)row.findViewById(R.id.phonetype);
            holder.networktype = (TextView)row.findViewById(R.id.networktype);
            holder.simcountrycode = (TextView)row.findViewById(R.id.simcountrycode);
            holder.simoperator = (TextView)row.findViewById(R.id.simoperator);
            holder.simserialno = (TextView)row.findViewById(R.id.simserialno);
            holder.subscriberid = (TextView)row.findViewById(R.id.subscriberid);
            holder.softwareversion = (TextView)row.findViewById(R.id.softwareversion);
            holder.operatorname = (TextView)row.findViewById(R.id.operatorname);


            row.setTag(holder);
        }
        else
        {
            holder = (PhoneStateHolder)row.getTag();
        }

        PhoneInfo phoneInfo = data.get(position);


        holder.deviceid.setText(phoneInfo.getDeviceId());
        holder.phonenumber.setText(phoneInfo.getPhoneNumber());
        holder.softwareversion.setText(phoneInfo.getSoftwareversion());
        holder.simserialno.setText(phoneInfo.getSimserialno());
        holder.simoperator.setText(phoneInfo.getSimoperator());
        holder.simcountrycode.setText(phoneInfo.getSimcountrycode());
        holder.networktype.setText(phoneInfo.getNetworktype());
        holder.phonetype.setText(phoneInfo.getPhonetype());
        holder.phonelocation.setText(phoneInfo.getPhonelocation());
        holder.subscriberid.setText(phoneInfo.getSubscriberid());
        holder.operatorname.setText(phoneInfo.getOperatorname());



        return row;
    }

    static class PhoneStateHolder
    {
        //the IMEI code
        TextView deviceid;
        //the phone number string for line 1, for example, the MSISDN for a GSM phone
        TextView phonenumber;
        //the software version number for the device, for example, the IMEI/SV for GSM phones
        TextView softwareversion;
        //the alphabetic name of current registered operator.
        TextView operatorname;
        //the ISO country code equivalent for the SIM provider's country code.
        TextView simcountrycode;
        //Service Provider Name (SPN).
        TextView simoperator;
        //serial number of the SIM
        TextView simserialno;
        //the unique subscriber ID, for example, the IMSI for a GSM phone
        TextView subscriberid;
        //the type indicating the radio technology (network type) currently in use on the device for data transmission.
        //EDGE,GPRS,UMTS  etc
        TextView networktype;
        //indicating the device phone type. This indicates the type of radio used to transmit voice calls
        //GSM,CDMA etc
        TextView phonetype;
        // Locationss
        TextView phonelocation;
    }


}
