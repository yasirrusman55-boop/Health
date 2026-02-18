const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

/**
 * Firestore trigger: when an alert document is created in `alerts` collection,
 * send an FCM message to clinicians topic. Ensure proper authentication rules.
 */
exports.onAlertCreated = functions.firestore
  .document('alerts/{alertId}')
  .onCreate(async (snap, context) => {
    const alert = snap.data();
    const payload = {
      notification: {
        title: 'SCDMonitor - High Risk Alert',
        body: alert && alert.message ? alert.message : 'A patient has elevated VOC risk.'
      },
      data: {
        patientId: alert && alert.patientId ? String(alert.patientId) : '',
      }
    };

    try {
      const response = await admin.messaging().sendToTopic('clinicians', payload);
      console.log('Sent alert to clinicians:', response);
    } catch (err) {
      console.error('Error sending clinician FCM:', err);
    }
  });
