# CPM OSC Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814887d7dd8f5201d87d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** CPM OSC Compat
- **Arquivo JAR:** `cpm-osc-compat-1.7.2.jar`
- **Versão 1.21.1:** 1.7.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual
- **Função:** Addon client-side para Customizable Player Models que recebe mensagens OSC/VMC por UDP e as converte em triggers/valores de animação CPM, incluindo value-layer/slider animations.
- **Dependências:** Customizable Player Models (CPM) é a dependência funcional principal. O addon recebe OSC/VMC por UDP; não é um servidor OSC bidirecional nem substitui o sistema de animações/modelos do CPM.
- **Sobreposição:** Complementa CPM; não substitui seu editor/model/avatar system. Pode sobrepor outras bridges de controle de animação apenas quando elas acionam o mesmo trigger/value, caso em que o settlement deve ser definido explicitamente.
- **Compatibilidade/Riscos:** Client-only e orientado a input de rede local/OSC. Riscos: porta UDP indisponível, mensagens malformadas/fora do contrato, animation trigger duplicado por outras bridges, version drift com CPM e exposição de listener além da interface/rede pretendida. O upstream declara explicitamente recebimento apenas de UDP.
- **Observações:** mod id `cpmoscc`; runtime 1.7.2. O projeto oficial declara suporte a OSC/VMC animation triggers, recebimento de pacotes UDP e value-layer/slider animations. É client-only.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + runtime 1.7.2 + CurseForge oficial Customizable Player Models OSC Compat 1.7.2 + documentação oficial do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cpmoscc
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — CPM OSC Compat 1.7.2, OSC/VMC→CPM authority, UDP receive-only boundary, value-layer settlement, security e lifecycle confirmados no QC global #112. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CPM OSC Compat 1.7.2 foi reconfirmado fisicamente e reconstruído como bridge client-side de OSC/VMC para CPM. A presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🎛️ Versão física confirmada: `cpm-osc-compat-1.7.2.jar`, mod id `cpmoscc`, runtime `1.7.2`. É uma bridge **client-side** entre OSC/VMC e o sistema de animações do Customizable Player Models.

## 1. Papel e authority
CPM OSC Compat recebe comandos externos via OSC/VMC e os converte em triggers/valores consumidos pelo CPM. O **CPM continua authority** de modelo, animação, value layers e avatar; esta bridge não cria um segundo sistema de player model.

## 2. Transporte OSC/VMC
A descrição oficial afirma que o mod aceita **somente recebimento de pacotes UDP**. Não documentar envio OSC, resposta bidirecional ou sessão confiável sem evidência adicional.
UDP não garante entrega, ordem ou retransmissão. Uma integração externa deve tolerar perda/reordenação sem transformar um pacote repetido em execução duplicada perigosa.

## 3. Animation triggers
Mensagens OSC podem acionar animações definidas no CPM. O identificador/trigger precisa resolver para uma animação existente no modelo ativo; pacote inválido não deve inventar animação nem alterar state persistente de gameplay.

## 4. Value layers / sliders
O upstream confirma suporte a **value layer (slider) animations**. Valores recebidos controlam parâmetros de animação/apresentação do avatar conforme o contract do CPM.
Não converter automaticamente slider visual em atributo server-side, movimento, dano ou outra mecânica de gameplay.

## 5. VMC
VMC é tratado como protocolo de entrada compatível no contexto do addon. A bridge deve mapear apenas superfícies que o projeto efetivamente suporta; não presumir cobertura completa de todo o ecossistema VMC/OSC.

## 6. Segurança de rede local
Como há listener UDP, validar interface/endereço/porta configurados pelo mod e pelo sistema operacional. Evitar expor a porta além da rede/interface pretendida sem necessidade.
Pacotes externos devem ser considerados input não confiável: payload inesperado não pode causar crash persistente nem modificar arquivos/modelos arbitrários.

## 7. Client-only boundary
A distribuição é client-only. Dedicated server não precisa executar o listener nem carregar classes da bridge.
O servidor continua authority de gameplay Minecraft; uma animação disparada por OSC no cliente não autoriza ações que o servidor não validaria.

## 8. Relação com Customizable Player Models
- CPM: editor, modelo, texturas, animações, avatar e sync correspondente;
- CPM OSC Compat: entrada OSC/VMC que aciona parâmetros/animações CPM.
Version drift entre os dois pode aparecer como trigger ausente, API incompatível ou crash de client bootstrap.

## 9. Lifecycle
Validar client bootstrap, criação/fechamento do listener, world join, troca de servidor/mundo, troca de modelo CPM, resource/model reload e shutdown.
Ao desconectar/trocar modelo, referências a animações antigas não devem permanecer ativas.

## 10. Concorrência com outras bridges
Outras integrações podem acionar a mesma animação por hotkey, script, API ou protocolo externo. Se duas fontes escrevem o mesmo value layer, o pack precisa definir precedence/ownership; não aplicar dois settlements apenas porque chegaram por canais diferentes.

## 11. Riscos
1. Porta UDP em uso ou indisponível.
2. Listener exposto além da interface necessária.
3. Pacote malformado causar erro no cliente.
4. Repetição/reordenação UDP duplicar triggers.
5. Modelo trocado deixar referência stale.
6. Value layer visual ser tratado como gameplay state.
7. Version drift CPM↔addon.
8. Duas bridges comandarem o mesmo parâmetro sem precedence.

## 12. Matriz de testes
1. Client boot com CPM + addon.
2. Enviar trigger OSC válido e confirmar uma execução.
3. Value-layer/slider em valores mínimos, intermediários e máximos.
4. Pacote desconhecido/malformado sem crash persistente.
5. Sequência rápida/repetida de UDP sem double settlement inesperado.
6. Trocar modelo CPM e confirmar que triggers antigos não vazam.
7. Disconnect/reconnect e client restart liberando/reabrindo listener corretamente.
8. Dedicated server sem dependência do addon.
9. Outra bridge/hotkey acionando a mesma animação para testar precedence.

## 13. Evidência
- modlist física atual: CPM OSC Compat 1.7.2;
- CurseForge oficial: addon client-side para CPM, OSC/VMC animation triggers;
- documentação oficial: recebe apenas UDP e suporta value-layer/slider animations;
- ausência de changelog granular da 1.7.2 para esta linha tratada fail-closed.

> 🔗 Boundary canônico: **OSC/VMC fornece input; CPM define e executa a animação do avatar**. Nenhum trigger visual vira autoridade de gameplay server-side.
