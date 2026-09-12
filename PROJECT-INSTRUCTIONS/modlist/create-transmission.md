# Create: Transmission!

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db816d9bfeea80745a33fd  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Transmission!
- **Arquivo JAR:** `createtransmission-1.2.2+neoforge-create6-1.21.1.jar`
- **Versão 1.21.1:** `1.2.2+neoforge-create6-1.21.1`
- **Categoria:** Tecnologia; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-transmission
- **Função:** Addon minimalista de Create que adiciona a Transmission Chain como alternativa compacta para transmitir rotação em layouts onde belts ou encased chain drives são menos convenientes.
- **Dependências:** Create 6.x. A build física é específica para NeoForge/Create 6 em Minecraft 1.21.1.
- **Compatibilidade/Riscos:** Escopo localizado: uma peça de transmissão cinética. Riscos concentram-se em placement/connection geometry, rendering e version drift com internals do Create 6. Não foram publicados throughput, stress impact ou limites próprios; esses valores não são inventados.
- **Sobreposição:** Alternativa específica a belts/encased chain drives para transmissão de rotação. Compartilha domínio cinético com Create, mas não substitui o sistema base nem justifica remoção automática por similaridade.
- **Observações:** mod id conforme runtime do JAR; runtime completo `1.2.2+neoforge-create6-1.21.1`. O addon adiciona Transmission Chain. 1.2.2 altera textura do item e adiciona traduções russa/ucraniana; não há delta funcional publicado nessa release.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime 1.2.2+neoforge-create6-1.21.1 + CurseForge/Modrinth oficiais da release 1.2.2 e descrição do projeto.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Transmission! 1.2.2 foi reconfirmado como `Instalado` e reconstruído proporcionalmente ao seu escopo mínimo; presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — Transmission Chain authority, Create-kinetics boundary, placement/geometry/render lifecycle e delta 1.2.2 de textura/traduções catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ⛓️ Versão física confirmada: `createtransmission-1.2.2+neoforge-create6-1.21.1.jar`, runtime `1.2.2+neoforge-create6-1.21.1`. O addon é propositalmente pequeno: adiciona **Transmission Chain** para layouts cinéticos Create.

## 1. Papel e authority
Create: Transmission! adiciona uma peça de conexão/transmissão. **Create continua authority de RPM, stress, kinetic-network state e machine behavior**; Transmission controla apenas seu block/item, connectivity e apresentação.

## 2. Transmission Chain
O projeto descreve Transmission Chain como alternativa limpa/compacta para transferir rotational power entre pontos que poderiam usar belts ou encased chain drives.

Não existe evidência pública versionada de throughput, torque, stress capacity ou ratio exclusivos; esta ficha não inventa números.

## 3. Conectividade
A peça deve participar da rede cinética de Create e refletir corretamente conexão/desconexão. Colocar/remover um segmento não pode deixar network edges fantasma nem criar uma segunda fonte de rotação.

## 4. Geometria
Material visual oficial mostra uso em layouts retos, diagonais/angulados e combinações de conexão. Essas imagens confirmam flexibilidade geométrica, mas não substituem contrato numérico de distância/ângulo; os limites reais precisam ser validados no runtime 1.2.2.

## 5. Relação com belts
Transmission Chain compartilha o objetivo de transportar rotação, mas não é automaticamente um belt de item transport. Se o bloco não oferece item logistics, integrações não devem assumir comportamento de belt apenas pela aparência de chain.

## 6. Relação com Encased Chain Drive
Pode ocupar layouts semelhantes a Encased Chain Drives. A semântica cinética final continua sendo decidida pela rede Create; scripts não devem aplicar ratio/stress adicional sem comprovação da build.

## 7. Encasing e aparência
Quando apresentações oficiais mostram versões encased/conectadas, tratar isso como state/model do addon. Resource packs podem alterar aparência sem mudar connectivity ou kinetic state server-side.

## 8. Delta 1.2.2
O changelog da **1.2.2** informa nova textura do item e traduções **russa e ucraniana**. Não há mudança funcional publicada para esta release; portanto não são atribuídos fixes mecânicos inexistentes.

## 9. Client/server
Kinetic connectivity/state pertence ao common/server junto ao Create. Models, item texture e translations são client-facing. Um render ausente não deve romper a conexão lógica, e um render conectado não prova que a network server-side está válida.

## 10. Lifecycle
Validar placement/removal, neighbor updates, chunk unload/reload, server restart, kinetic network rebuild e resource reload. Se usado em contraptions, validar apenas os casos realmente suportados pelo runtime em mundo de teste, sem assumir mobilidade universal.

## 11. Riscos
1. Conexão lógica permanecer após remoção.
2. Network rebuild perder segmento.
3. Geometry visual divergir da conexão real.
4. Version drift com Create 6 alterar kinetic integration.
5. Integração externa assumir item transport inexistente.
6. Resource pack/render esconder estado sem alterar lógica.

## 12. Matriz de testes
1. Dedicated server boot.
2. Linha curta de Transmission Chains entre componentes Create.
3. Placement/removal com rede em movimento.
4. Layouts angulados mostrados pelo projeto, validando limites reais.
5. Overstress/network stop propagando corretamente pelo Create.
6. Chunk border e unload/reload.
7. Server restart com rede montada.
8. Resource reload e textura 1.2.2.
9. Comparação funcional com belt e Encased Chain Drive sem assumir equivalência.

## 13. Evidência
- modlist física 08/09/2026: Transmission 1.2.2 full runtime string;
- projeto oficial: Transmission Chain como alternativa compacta/limpa para transmissão de rotação;
- material oficial: arrangements de conexão/encasing;
- changelog 1.2.2: nova item texture + traduções russa/ucraniana.

> 🔒 Boundary canônico: **Transmission adiciona connectivity; Create continua decidindo a física cinética**. Não existem números próprios de RPM/stress autorizados pela evidência pública consultada.
