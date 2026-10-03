package com.pravesh.software

import android.content.*
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val upiId = "9871073225@upi" // CHANGE THIS if your actual UPI VPA is different
    private val phone = "9871073225"
    private val services = listOf(
        Service("SEO Services", "Improve Google visibility, technical SEO, local SEO and content optimization.", "SEO"),
        Service("Website Development", "Business websites, responsive UI, CMS integration and deployment.", "WEB"),
        Service("Landing Page Development", "High-converting landing pages for ads, leads and campaigns.", "LP"),
        Service("Software Testing", "Manual testing, functional testing, regression testing and QA support.", "QA")
    )
    private var selected: Service? = null
    private var amount = ""

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showHome() }

    private fun base(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(245,247,251))
    }
    private fun tv(text: String, size: Float, color: Int = Color.rgb(23,32,51), bold: Boolean = false): TextView = TextView(this).apply {
        this.text=text; textSize=size; setTextColor(color); setPadding(0,4,0,4); if(bold) setTypeface(null,1)
    }
    private fun button(text: String, primary: Boolean = true): Button = Button(this).apply {
        this.text=text; isAllCaps=false; setTextColor(if(primary) Color.WHITE else Color.rgb(23,32,51));
        setBackgroundColor(if(primary) Color.rgb(21,94,239) else Color.WHITE); setPadding(24,8,24,8)
    }
    private fun showHome() {
        val root=base(); val scroll=ScrollView(this); val content=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(22,22,22,28)}
        content.addView(tv("PRAVESH SOFTWARE SOLUTION",24,Color.rgb(21,94,239),true))
        content.addView(tv("Digital services for your business",15,Color.rgb(102,112,133)))
        content.addView(space(16))
        val hero=TextView(this).apply{ text="Build. Grow. Test.\nOne trusted technology partner."; textSize=26f; setTextColor(Color.rgb(23,32,51)); setTypeface(null,1); setPadding(0,12,0,12)}
        content.addView(hero); content.addView(tv("Choose a service below and pay your booking/advance amount securely using UPI.",15,Color.rgb(102,112,133))); content.addView(space(14))
        services.forEach { s -> content.addView(serviceCard(s)); content.addView(space(10)) }
        content.addView(space(12)); content.addView(tv("Payment UPI",14,Color.rgb(102,112,133),true)); content.addView(tv(phone,18,Color.rgb(23,32,51),true))
        val contact=button("Contact / WhatsApp",false); contact.setOnClickListener{ openWhatsApp() }; content.addView(contact)
        scroll.addView(content); root.addView(scroll, LinearLayout.LayoutParams(-1,0,1f)); setContentView(root)
    }
    private fun serviceCard(s: Service): LinearLayout { 
        val box=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(18,16,18,16);setBackgroundColor(Color.WHITE)}
        box.addView(tv(s.name,19,Color.rgb(23,32,51),true)); box.addView(tv(s.description,14,Color.rgb(102,112,133))); val b=button("Select Service")
        b.setOnClickListener{selected=s; showPayment()}; box.addView(b); return box
    }
    private fun showPayment(){
        val s=selected ?: return; val root=base(); val content=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(22,22,22,28)}
        val back=button("← Back",false); back.setOnClickListener{showHome()}; content.addView(back)
        content.addView(tv(s.name,25,Color.rgb(23,32,51),true)); content.addView(tv(s.description,14,Color.rgb(102,112,133))); content.addView(space(18))
        content.addView(tv("Enter payment amount (₹)",15,Color.rgb(23,32,51),true)); val amountBox=EditText(this).apply{hint="e.g. 5000";inputType=2;setText(amount);setTextSize(18f);setPadding(16,14,16,14)}; content.addView(amountBox)
        content.addView(space(12)); val pay=button("Pay with UPI"); pay.setOnClickListener{amount=amountBox.text.toString(); startUPI()}; content.addView(pay)
        val qr=button("Show UPI QR Code",false); qr.setOnClickListener{amount=amountBox.text.toString(); showQr()}; content.addView(qr)
        content.addView(space(18)); content.addView(tv("UPI ID",13,Color.rgb(102,112,133))); content.addView(tv(upiId,17,Color.rgb(23,32,51),true)); content.addView(space(8)); content.addView(tv("After payment, please send the transaction screenshot/reference number to WhatsApp for confirmation. This app does not automatically verify payments.",13,Color.rgb(102,112,133)))
        val wa=button("Send Payment Proof on WhatsApp",false); wa.setOnClickListener{openWhatsApp()}; content.addView(wa)
        val scroll=ScrollView(this);scroll.addView(content);root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f));setContentView(root)
    }
    private fun startUPI(){ val a=amount.toDoubleOrNull(); if(a==null||a<=0){toast("Enter a valid amount");return}; val uri=Uri.parse("upi://pay?pa=${Uri.encode(upiId)}&pn=${Uri.encode("Pravesh Software Solution")}&am=${String.format("%.2f",a)}&cu=INR&tn=${Uri.encode(selected?.name ?: "Service Payment")}"); try{startActivity(Intent(Intent.ACTION_VIEW,uri))}catch(e:Exception){toast("No UPI app found. Please scan the QR code instead.")}}
    private fun showQr(){ val a=amount.toDoubleOrNull(); if(a==null||a<=0){toast("Enter a valid amount first");return}; val img=ImageView(this).apply{setImageResource(com.pravesh.software.R.drawable.upi_qr);setPadding(18,18,18,18)}; AlertDialog.Builder(this).setTitle("Scan to Pay ₹${String.format("%.2f",a)}").setView(img).setMessage("UPI: $upiId\nEnter ₹${String.format("%.2f",a)} in your UPI app if the amount is not prefilled.").setPositiveButton("Done",null).show() }
    private fun openWhatsApp(){ val u=Uri.parse("https://wa.me/91$phone?text=${Uri.encode("Hello Pravesh Software Solution, I want to enquire about your services.")}");startActivity(Intent(Intent.ACTION_VIEW,u)) }
    private fun toast(s:String){Toast.makeText(this,s,Toast.LENGTH_SHORT).show()}
    private fun space(h:Int)=Space(this).apply{minimumHeight=h}
    data class Service(val name:String,val description:String,val tag:String)
}
