var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'moja.pl1945@gmail.com',
    pass: 'mojapl1945'
  }
});

exports.sendReapet = function(response, email, newPassword){
  var mailOptions = {
    from: 'moja.pl1945@gmail.com',
    to: email,
    subject: 'MojaPŁ - przypomnienie hasła',
    html: '<h1>Witaj!</h1><p>Twoje nowe hasło to : '+newPassword+'</p><p>Hasło należy zmienić po pierwszym zalogowaniu.</p>'
  };

  transporter.sendMail(mailOptions, function(error, info){
    if (error) {
      throw error;
      response.status(403);
      response.json({
          status:false,
          message:'E-mail sender failed.'
      })
    } else {
      response.status(200);
      response.json({
          status:true,
          message:'Repeate password e-mail sent.'
      })
    }
  }); 



}


