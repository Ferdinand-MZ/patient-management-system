<img width="137" height="150" alt="patient-management-system" src="https://github.com/user-attachments/assets/3538bbac-a0ee-46bd-856f-0dbd88b50bd8" /># Patient Management System

A microservices-based patient management backend built with **Spring Boot**, featuring JWT authentication, gRPC inter-service communication, and Kafka event streaming — deployed on AWS (LocalStack for local dev) using ECS Fargate.

---

## System Architecture

![Uploading<svg width="100%" viewBox="0 0 680 742" role="img" style="" xmlns="http://www.w3.org/2000/svg">
  <title style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">Patient Management Microservices Architecture</title>
  <desc style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">System design showing how the frontend communicates through API Gateway to microservices using REST, JWT auth, gRPC, and Kafka.</desc>
  <defs>
    <marker id="arrow" viewBox="0 0 10 10" refX="8" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M2 1L8 5L2 9" fill="none" stroke="context-stroke" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
    </marker>
  <mask id="imagine-text-gaps-7kzr25" maskUnits="userSpaceOnUse"><rect x="0" y="0" width="680" height="742" fill="white"/><rect x="281.624755859375" y="25.6666259765625" width="116.55412292480469" height="20.666746139526367" fill="black" rx="2"/><rect x="305.09710693359375" y="42.22218704223633" width="71.35466003417969" height="19.55562973022461" fill="black" rx="2"/><rect x="351" y="72.77772521972656" width="81.20957946777344" height="19.55562973022461" fill="black" rx="2"/><rect x="292.48419189453125" y="111.66663360595703" width="95.79151916503906" height="20.666746139526367" fill="black" rx="2"/><rect x="252.46664428710938" y="130.22219848632812" width="175.49862670898438" height="19.55562973022461" fill="black" rx="2"/><rect x="46.888885498046875" y="170.77772521972656" width="83.72337341308594" height="19.55562973022461" fill="black" rx="2"/><rect x="349.8888854980469" y="178.77772521972656" width="97.54228973388672" height="19.55562973022461" fill="black" rx="2"/><rect x="81.95464324951172" y="247.6666259765625" width="95.68762969970703" height="20.666746139526367" fill="black" rx="2"/><rect x="55.04479217529297" y="266.2221984863281" width="150.7415313720703" height="19.55562973022461" fill="black" rx="2"/><rect x="156" y="170.77772521972656" width="72.75520324707031" height="19.55562973022461" fill="black" rx="2"/><rect x="285.904296875" y="227.66664123535156" width="108.90115356445312" height="20.666746139526367" fill="black" rx="2"/><rect x="264.0986328125" y="246.22219848632812" width="153.76058959960938" height="19.55562973022461" fill="black" rx="2"/><rect x="454.888916015625" y="220.77772521972656" width="41.45854568481445" height="19.55562973022461" fill="black" rx="2"/><rect x="524.4112548828125" y="227.66664123535156" width="101.87869262695312" height="20.666746139526367" fill="black" rx="2"/><rect x="516.546630859375" y="246.22219848632812" width="115.795654296875" height="19.55562973022461" fill="black" rx="2"/><rect x="507.1368713378906" y="292.2221984863281" width="135.31126403808594" height="19.55562973022461" fill="black" rx="2"/><rect x="507.1368713378906" y="308.2221984863281" width="135.72630310058594" height="19.55562973022461" fill="black" rx="2"/><rect x="344" y="301.7777404785156" width="93.41989135742188" height="19.55562973022461" fill="black" rx="2"/><rect x="344" y="315.7777404785156" width="119.15666961669922" height="19.55562973022461" fill="black" rx="2"/><rect x="292.94427490234375" y="357.6666259765625" width="95.0191879272461" height="20.666746139526367" fill="black" rx="2"/><rect x="260.77398681640625" y="376.2221984863281" width="159.10589599609375" height="19.55562973022461" fill="black" rx="2"/><rect x="342.8888854980469" y="421.7777099609375" width="95.51622009277344" height="19.55562973022461" fill="black" rx="2"/><rect x="276.90252685546875" y="467.6666564941406" width="125.7848129272461" height="20.666746139526367" fill="black" rx="2"/><rect x="270.7392883300781" y="486.2221984863281" width="139.3357696533203" height="19.55562973022461" fill="black" rx="2"/><rect x="32.88888168334961" y="575.7777709960938" width="188.96609497070312" height="19.55562973022461" fill="black" rx="2"/><rect x="43.860862731933594" y="603.6666870117188" width="119.88848876953125" height="20.666746139526367" fill="black" rx="2"/><rect x="44.87649154663086" y="620.2222290039062" width="115.63960266113281" height="19.55562973022461" fill="black" rx="2"/><rect x="194.3817138671875" y="603.6666870117188" width="117.25860595703125" height="20.666746139526367" fill="black" rx="2"/><rect x="184.45111083984375" y="620.2222290039062" width="137.8208770751953" height="19.55562973022461" fill="black" rx="2"/><rect x="358.5918273925781" y="603.6666870117188" width="89.51952362060547" height="20.666746139526367" fill="black" rx="2"/><rect x="325.5708312988281" y="620.2222290039062" width="154.09024047851562" height="19.55562973022461" fill="black" rx="2"/><rect x="494.5361633300781" y="603.6666870117188" width="134.73278045654297" height="20.666746139526367" fill="black" rx="2"/><rect x="496.72369384765625" y="620.2222290039062" width="131.4829864501953" height="19.55562973022461" fill="black" rx="2"/><rect x="70" y="704.7777099609375" width="72.82929229736328" height="19.55562973022461" fill="black" rx="2"/><rect x="178.88888549804688" y="704.7777099609375" width="82.60027313232422" height="19.55562973022461" fill="black" rx="2"/><rect x="300.888916015625" y="704.7777099609375" width="103.10462188720703" height="19.55562973022461" fill="black" rx="2"/><rect x="442.0000305175781" y="704.7777099609375" width="75.37651824951172" height="19.55562973022461" fill="black" rx="2"/><rect x="548" y="704.7777099609375" width="88.20298767089844" height="19.55562973022461" fill="black" rx="2"/></mask></defs>

  <!-- CLIENT -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="270" y="18" width="140" height="44" rx="8" stroke-width="0.5" style="fill:rgb(68, 68, 65);stroke:rgb(180, 178, 169);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="340" y="36" text-anchor="middle" dominant-baseline="central" style="fill:rgb(211, 209, 199);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">Client / Browser</text>
    <text x="340" y="52" text-anchor="middle" dominant-baseline="central" style="fill:rgb(180, 178, 169);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">REST HTTP</text>
  </g>

  <!-- Arrow: client -> gateway -->
  <line x1="340" y1="62" x2="340" y2="102" stroke="#378ADD" marker-end="url(#arrow)" style="fill:none;stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="355" y="87" fill="#378ADD" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">HTTPS :4004</text>

  <!-- API GATEWAY -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="200" y="102" width="280" height="56" rx="8" stroke-width="0.5" style="fill:rgb(12, 68, 124);stroke:rgb(133, 183, 235);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="340" y="122" text-anchor="middle" dominant-baseline="central" style="fill:rgb(181, 212, 244);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">API Gateway</text>
    <text x="340" y="140" text-anchor="middle" dominant-baseline="central" style="fill:rgb(133, 183, 235);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">Spring Cloud Gateway · :4004</text>
  </g>

  <!-- Arrow: gateway -> auth-service (validate) -->
  <path d="M200 128 L100 128 L100 238" fill="none" stroke="#1D9E75" stroke-width="1.5" marker-end="url(#arrow)" stroke-dasharray="5 3" mask="url(#imagine-text-gaps-7kzr25)" style="fill:none;stroke:rgb(29, 158, 117);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-dasharray:5px, 3px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="52" y="185" fill="#1D9E75" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">validate JWT</text>

  <!-- Arrow: gateway -> patient-service -->
  <line x1="340" y1="158" x2="340" y2="218" stroke="#378ADD" marker-end="url(#arrow)" style="fill:none;stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="355" y="193" fill="#378ADD" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">/api/patients/**</text>

  <!-- AUTH SERVICE -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="30" y="238" width="200" height="56" rx="8" stroke-width="0.5" style="fill:rgb(8, 80, 65);stroke:rgb(93, 202, 165);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="130" y="258" text-anchor="middle" dominant-baseline="central" style="fill:rgb(159, 225, 203);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">Auth Service</text>
    <text x="130" y="276" text-anchor="middle" dominant-baseline="central" style="fill:rgb(93, 202, 165);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">JWT · :4005 · PostgreSQL</text>
  </g>

  <!-- Arrow: gateway -> auth for /auth/login -->
  <path d="M200 125 L155 125 L155 238" fill="none" stroke="#1D9E75" stroke-width="1.5" marker-end="url(#arrow)" style="fill:none;stroke:rgb(29, 158, 117);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="160" y="185" fill="#1D9E75" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">POST /login</text>

  <!-- PATIENT SERVICE -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="240" y="218" width="200" height="56" rx="8" stroke-width="0.5" style="fill:rgb(12, 68, 124);stroke:rgb(133, 183, 235);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="340" y="238" text-anchor="middle" dominant-baseline="central" style="fill:rgb(181, 212, 244);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">Patient Service</text>
    <text x="340" y="256" text-anchor="middle" dominant-baseline="central" style="fill:rgb(133, 183, 235);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">REST · :4000 · PostgreSQL</text>
  </g>

  <!-- Arrow: patient -> billing (gRPC) -->
  <line x1="440" y1="246" x2="490" y2="246" stroke="#BA7517" stroke-width="1.5" marker-end="url(#arrow)" style="fill:none;stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="460" y="235" fill="#BA7517" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">gRPC</text>

  <!-- BILLING SERVICE -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="490" y="218" width="170" height="56" rx="8" stroke-width="0.5" style="fill:rgb(99, 56, 6);stroke:rgb(239, 159, 39);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="575" y="238" text-anchor="middle" dominant-baseline="central" style="fill:rgb(250, 199, 117);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">Billing Service</text>
    <text x="575" y="256" text-anchor="middle" dominant-baseline="central" style="fill:rgb(239, 159, 39);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">gRPC server · :9001</text>
  </g>

  <!-- gRPC label detail box -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="492" y="285" width="166" height="42" rx="6" stroke-width="0.5" style="fill:rgb(99, 56, 6);stroke:rgb(239, 159, 39);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="575" y="302" text-anchor="middle" dominant-baseline="central" style="fill:rgb(239, 159, 39);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">CreateBillingAccount(</text>
    <text x="575" y="318" text-anchor="middle" dominant-baseline="central" style="fill:rgb(239, 159, 39);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">patientId, name, email)</text>
  </g>
  <line x1="575" y1="274" x2="575" y2="285" stroke="var(--t)" stroke-width="0.5" stroke-dasharray="3 2" style="fill:rgb(0, 0, 0);stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-dasharray:3px, 2px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>

  <!-- Arrow: patient -> kafka -->
  <line x1="340" y1="274" x2="340" y2="348" stroke="#993C1D" stroke-width="1.5" marker-end="url(#arrow)" style="fill:none;stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="348" y="316" fill="#993C1D" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">Protobuf event</text>
  <text x="348" y="330" fill="#993C1D" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">PATIENT_CREATED</text>

  <!-- KAFKA BROKER -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="200" y="348" width="280" height="56" rx="8" stroke-width="0.5" style="fill:rgb(113, 43, 19);stroke:rgb(240, 153, 123);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="340" y="368" text-anchor="middle" dominant-baseline="central" style="fill:rgb(245, 196, 179);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">Kafka Broker</text>
    <text x="340" y="386" text-anchor="middle" dominant-baseline="central" style="fill:rgb(240, 153, 123);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">Topic: "patient" · AWS MSK</text>
  </g>

  <!-- Arrow: kafka -> analytics -->
  <line x1="340" y1="404" x2="340" y2="458" stroke="#993C1D" stroke-width="1.5" marker-end="url(#arrow)" style="fill:none;stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="348" y="436" fill="#993C1D" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">consume bytes</text>

  <!-- ANALYTICS SERVICE -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="200" y="458" width="280" height="56" rx="8" stroke-width="0.5" style="fill:rgb(60, 52, 137);stroke:rgb(175, 169, 236);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="340" y="478" text-anchor="middle" dominant-baseline="central" style="fill:rgb(206, 203, 246);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">Analytics Service</text>
    <text x="340" y="496" text-anchor="middle" dominant-baseline="central" style="fill:rgb(175, 169, 236);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">Kafka consumer · :4002</text>
  </g>

  <!-- INFRA CONTAINER (dashed) -->
  <rect x="22" y="570" width="636" height="112" rx="12" fill="none" stroke="var(--t)" stroke-width="0.5" stroke-dasharray="6 4" opacity="0.5" style="fill:none;stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-dasharray:6px, 4px;stroke-linecap:butt;stroke-linejoin:miter;opacity:0.5;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="38" y="590" fill="var(--color-text-secondary)" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">AWS / LocalStack infrastructure</text>

  <!-- INFRA nodes -->
  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="38" y="596" width="130" height="44" rx="6" stroke-width="0.5" style="fill:rgb(68, 68, 65);stroke:rgb(180, 178, 169);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="103" y="614" text-anchor="middle" dominant-baseline="central" style="fill:rgb(211, 209, 199);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">PostgreSQL RDS</text>
    <text x="103" y="630" text-anchor="middle" dominant-baseline="central" style="fill:rgb(180, 178, 169);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">Auth + Patient DBs</text>
  </g>

  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="188" y="596" width="130" height="44" rx="6" stroke-width="0.5" style="fill:rgb(68, 68, 65);stroke:rgb(180, 178, 169);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="253" y="614" text-anchor="middle" dominant-baseline="central" style="fill:rgb(211, 209, 199);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">AWS MSK Kafka</text>
    <text x="253" y="630" text-anchor="middle" dominant-baseline="central" style="fill:rgb(180, 178, 169);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">Managed Kafka cluster</text>
  </g>

  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="338" y="596" width="130" height="44" rx="6" stroke-width="0.5" style="fill:rgb(68, 68, 65);stroke:rgb(180, 178, 169);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="403" y="614" text-anchor="middle" dominant-baseline="central" style="fill:rgb(211, 209, 199);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">ECS Fargate</text>
    <text x="403" y="630" text-anchor="middle" dominant-baseline="central" style="fill:rgb(180, 178, 169);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">All services containerized</text>
  </g>

  <g style="fill:rgb(0, 0, 0);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto">
    <rect x="488" y="596" width="148" height="44" rx="6" stroke-width="0.5" style="fill:rgb(68, 68, 65);stroke:rgb(180, 178, 169);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
    <text x="562" y="614" text-anchor="middle" dominant-baseline="central" style="fill:rgb(211, 209, 199);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:14px;font-weight:500;text-anchor:middle;dominant-baseline:central">App Load Balancer</text>
    <text x="562" y="630" text-anchor="middle" dominant-baseline="central" style="fill:rgb(180, 178, 169);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:middle;dominant-baseline:central">Exposes API Gateway</text>
  </g>

  <!-- Connect infra to services with dashed lines -->
  <line x1="130" y1="294" x2="103" y2="596" stroke="var(--t)" stroke-width="0.5" stroke-dasharray="3 3" opacity="0.4" mask="url(#imagine-text-gaps-7kzr25)" style="fill:rgb(0, 0, 0);stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-dasharray:3px, 3px;stroke-linecap:butt;stroke-linejoin:miter;opacity:0.4;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <line x1="280" y1="274" x2="253" y2="596" stroke="var(--t)" stroke-width="0.5" stroke-dasharray="3 3" opacity="0.4" mask="url(#imagine-text-gaps-7kzr25)" style="fill:rgb(0, 0, 0);stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-dasharray:3px, 3px;stroke-linecap:butt;stroke-linejoin:miter;opacity:0.4;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>

  <!-- LEGEND -->
  <rect x="22" y="698" width="636" height="34" rx="6" fill="none" stroke="var(--color-border-tertiary)" stroke-width="0.5" style="fill:none;stroke:rgba(222, 220, 209, 0.15);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <line x1="38" y1="715" x2="68" y2="715" stroke="#378ADD" stroke-width="1.5" marker-end="url(#arrow)" style="fill:rgb(0, 0, 0);stroke:rgb(55, 138, 221);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="74" y="719" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">REST/HTTP</text>
  <line x1="148" y1="715" x2="178" y2="715" stroke="#1D9E75" stroke-width="1.5" stroke-dasharray="5 3" marker-end="url(#arrow)" style="fill:rgb(0, 0, 0);stroke:rgb(29, 158, 117);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-dasharray:5px, 3px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="184" y="719" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">JWT validate</text>
  <line x1="270" y1="715" x2="300" y2="715" stroke="#BA7517" stroke-width="1.5" marker-end="url(#arrow)" style="fill:rgb(0, 0, 0);stroke:rgb(186, 117, 23);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="306" y="719" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">gRPC (Protobuf)</text>
  <line x1="410" y1="715" x2="440" y2="715" stroke="#993C1D" stroke-width="1.5" marker-end="url(#arrow)" style="fill:rgb(0, 0, 0);stroke:rgb(153, 60, 29);color:rgb(255, 255, 255);stroke-width:1.5px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="446" y="719" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">Kafka event</text>
  <line x1="516" y1="715" x2="546" y2="715" stroke="var(--t)" stroke-width="0.5" stroke-dasharray="3 3" opacity="0.5" mask="url(#imagine-text-gaps-7kzr25)" style="fill:rgb(0, 0, 0);stroke:rgb(156, 154, 146);color:rgb(255, 255, 255);stroke-width:0.5px;stroke-dasharray:3px, 3px;stroke-linecap:butt;stroke-linejoin:miter;opacity:0.5;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:16px;font-weight:400;text-anchor:start;dominant-baseline:auto"/>
  <text x="552" y="719" style="fill:rgb(194, 192, 182);stroke:none;color:rgb(255, 255, 255);stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;opacity:1;font-family:&quot;Anthropic Sans&quot;, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, sans-serif;font-size:12px;font-weight:400;text-anchor:start;dominant-baseline:auto">infrastructure</text>
</svg> patient-management-system.svg…]()


### Communication patterns

| Pattern | Used between | Protocol |
|---|---|---|
| REST / HTTP | Client → API Gateway → services | HTTP/JSON |
| JWT validation | API Gateway → Auth Service | HTTP GET `/validate` |
| Synchronous RPC | Patient Service → Billing Service | gRPC + Protobuf |
| Async event streaming | Patient Service → Analytics Service | Kafka + Protobuf |

---

## Services

### API Gateway · `:4004`
Entry point for all client traffic. Built on Spring Cloud Gateway. Routes requests to downstream services and enforces JWT authentication on protected routes via a custom `JwtValidationGatewayFilterFactory` that calls Auth Service's `/validate` endpoint before forwarding.

| Route | Destination | Auth required |
|---|---|---|
| `POST /auth/login` | Auth Service | No |
| `GET /auth/validate` | Auth Service | No |
| `/api/patients/**` | Patient Service | Yes — JWT |
| `/api-docs/patients` | Patient Service `/v3/api-docs` | No |
| `/api-docs/auth` | Auth Service `/v3/api-docs` | No |

---

### Auth Service · `:4005`
Handles user authentication and JWT lifecycle.

- `POST /login` — validates credentials against PostgreSQL, returns a signed JWT
- `GET /validate` — verifies a JWT token; called by the API Gateway filter on every protected request
- Uses Spring Security + JJWT for token signing and validation
- Has its own dedicated PostgreSQL database

---

### Patient Service · `:4000`
Core business service. Manages patient records via a REST API.

**Endpoints** (all require `Authorization: Bearer <token>` via API Gateway):

| Method | Path | Description |
|---|---|---|
| `GET` | `/patients` | List all patients |
| `POST` | `/patients` | Create a new patient |
| `PUT` | `/patients/{id}` | Update a patient |
| `DELETE` | `/patients/{id}` | Delete a patient |

**On patient creation**, the service does two things concurrently:
1. Calls **Billing Service** via gRPC (`CreateBillingAccount`) — synchronous, blocking
2. Publishes a `PATIENT_CREATED` Protobuf event to Kafka topic `"patient"` — asynchronous

Has its own dedicated PostgreSQL database. OpenAPI docs available at `/v3/api-docs`.

---

### Billing Service · `:4001` (HTTP) · `:9001` (gRPC)
Exposes a gRPC server that handles billing account creation. Receives `BillingRequest` (patientId, name, email) from Patient Service and returns a `BillingResponse` (accountId, status).

Proto definition:
```protobuf
service BillingService {
  rpc CreateBillingAccount (BillingRequest) returns (BillingResponse);
}
```

No database — stateless processing only.

---

### Analytics Service · `:4002`
Kafka consumer that listens on the `"patient"` topic (group: `analytics-service`). Deserializes Protobuf `PatientEvent` messages and processes patient lifecycle events (currently logs; designed to be extended with analytics logic).

Proto definition:
```protobuf
message PatientEvent {
  string patientId = 1;
  string name      = 2;
  string email     = 3;
  string event_type = 4;  // e.g. "PATIENT_CREATED"
}
```

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| API Gateway | Spring Cloud Gateway |
| Auth | Spring Security + JJWT |
| ORM | Spring Data JPA + Hibernate |
| Database | PostgreSQL (RDS) |
| RPC | gRPC + Protobuf |
| Messaging | Apache Kafka (AWS MSK) |
| Serialization | Protocol Buffers (proto3) |
| API Docs | SpringDoc OpenAPI (Swagger) |
| Infrastructure | AWS CDK (Java) |
| Local dev infra | LocalStack |
| Containerization | Docker + ECS Fargate |
| Testing | JUnit 5 + REST Assured |

---

## Project Structure

```
patient-management/
├── api-gateway/          # Spring Cloud Gateway + JWT filter
├── auth-service/         # Login, JWT issue & validate
├── patient-service/      # Patient CRUD, gRPC client, Kafka producer
├── billing-service/      # gRPC server for billing accounts
├── analytics-service/    # Kafka consumer for patient events
├── infrastructure/       # AWS CDK stack (LocalStack)
├── integration-tests/    # End-to-end REST Assured tests
├── api-requests/         # HTTP request samples (auth & patient)
└── grpc-requests/        # gRPC request samples (billing)
```

---

## Getting Started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven
- LocalStack CLI (for local AWS infra)
- AWS CLI

### Run locally

**1. Start infrastructure (PostgreSQL + Kafka via LocalStack)**

```bash
cd infrastructure
./localstack-deploy.sh
```

This synthesizes the CDK stack and deploys it to LocalStack, spinning up:
- 2× PostgreSQL RDS instances (auth-service-db, patient-service-db)
- AWS MSK Kafka cluster
- ECS Fargate services for all microservices

**2. Build all services**

```bash
# From each service directory
./mvnw clean package -DskipTests

# Or build all at once from root
for service in auth-service patient-service billing-service analytics-service api-gateway; do
  cd $service && ./mvnw clean package -DskipTests && cd ..
done
```

**3. Run services (local dev without Docker)**

Start each service in order:

```bash
# Auth Service
cd auth-service && ./mvnw spring-boot:run

# Billing Service
cd billing-service && ./mvnw spring-boot:run

# Patient Service
cd patient-service && ./mvnw spring-boot:run

# Analytics Service
cd analytics-service && ./mvnw spring-boot:run

# API Gateway (last)
cd api-gateway && ./mvnw spring-boot:run
```

---

## API Usage

### 1. Login and get a JWT

```http
POST http://localhost:4004/auth/login
Content-Type: application/json

{
  "email": "testuser@test.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Create a patient

```http
POST http://localhost:4004/api/patients
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "address": "123 Main St",
  "dateOfBirth": "1990-01-15",
  "registeredDate": "2024-01-01"
}
```

### 3. Get all patients

```http
GET http://localhost:4004/api/patients
Authorization: Bearer <token>
```

### 4. Update a patient

```http
PUT http://localhost:4004/api/patients/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "address": "456 Oak Ave",
  "dateOfBirth": "1992-05-20"
}
```

### 5. Delete a patient

```http
DELETE http://localhost:4004/api/patients/{id}
Authorization: Bearer <token>
```

---

## Running Tests

```bash
# Unit tests (per service)
cd patient-service && ./mvnw test

# Integration tests (requires all services running on :4004)
cd integration-tests && ./mvnw test
```

The integration tests use REST Assured to:
1. Log in via the API Gateway and obtain a JWT
2. Call the `/api/patients` endpoint with the token and assert a `200` response

---

## API Documentation

Swagger UI is available via the API Gateway when services are running:

- Patient Service docs: `http://localhost:4004/api-docs/patients`
- Auth Service docs: `http://localhost:4004/api-docs/auth`

---

## Infrastructure (AWS CDK)

The `infrastructure/` module is an AWS CDK app written in Java that provisions:

| Resource | Detail |
|---|---|
| VPC | 2 availability zones |
| PostgreSQL RDS | `db.t2.micro`, 20GB — one per stateful service |
| AWS MSK | Kafka 2.8.0, 1 broker, `kafka.m5.xlarge` |
| ECS Cluster | Fargate, 256 CPU / 512MB per task |
| Application Load Balancer | Fronts the API Gateway only |
| CloudWatch Logs | 1-day retention per service |

All services run without a public IP — only the API Gateway is exposed via the ALB.

**Startup dependency order:**
```
PostgreSQL RDS (health check) → Auth Service
PostgreSQL RDS (health check) → Patient Service
MSK Kafka cluster             → Analytics Service
Billing Service               → Patient Service (gRPC dependency)
MSK Kafka cluster             → Patient Service (event producer)
```
