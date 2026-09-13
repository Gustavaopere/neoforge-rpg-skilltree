# Create: Bits 'n' Bobs

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81539017f4fb97ee558c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Bits 'n' Bobs
- **Arquivo JAR:** `bits_n_bobs-2.3.2.jar`
- **Versão 1.21.1:** 2.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Visual, Automação
- **Função:** Addon Create de mecânica/decoração com dyeable pipes/tanks, Cogwheel Chain Drives, Flanged Cogwheels, Chain Carriage, Flywheel Bearing/kinetic battery, Chain Pulley, encasings e famílias decorativas.
- **Dependências:** Create; Sable Companion 1.4.2 está embarcado em META-INF/jarjar e não é entrada top-level. Pack usa Create 6.0.10. Bits 'n Tracks não está top-level no snapshot atual; Create Tracks+ 1.0.6b6 está presente, mas não foi presumido como consumer/substituto sem evidência.
- **Sobreposição:** Sobreposição mecânica/decorativa com outros addons Create. Conflito visual com Thermochemical Cogwheels de Sulfuric Resonance permanece documentado historicamente, mas o provider não está presente no snapshot atual.
- **Compatibilidade/Riscos:** Riscos atuais: kinetic duplication, contraption lifecycle, feature suppression/config e regressão de Chain Drive/cogwheel integrations. A 2.3.2 corrige Chain Drive sobrescrevendo `gears n kinetics` e restaura assinatura antiga para evitar crash com Bits 'n Tracks. O antigo conflito visual com Sulfuric Resonance é histórico, não ativo neste snapshot.
- **Observações:** 2.3.2: corrige Chain Drive sobrescrevendo `gears n kinetics`, adiciona mensagem de falha ausente ao usar chain em outros cogwheels e restaura `CogwheelChainRenderGeometryBuilder#renderChainSlowerButWithoutGaps` deprecated para evitar crash com Bits 'n Tracks. Conteúdo 2.3.1 catalogado permanece baseline funcional válida salvo esses deltas.
- **Procedência:** modlist.txt física atual de 08/09/2026 + metadata/JAR jarjar + CurseForge oficial Create: Bits 'n' Bobs 2.3.2 + decisão curatorial já registrada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-bits-n-bobs
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime físico `bits_n_bobs-2.3.2.jar` reconciliado; três fixes oficiais 2.3.2 incorporados; decisão `Manter` preservada. Antigo conflito visual com Sulfuric Resonance mantido apenas como histórico porque o provider está ausente do snapshot atual. Runtime QA não executado.
- **Histórico da decisão:** 2026-09-06 — decisão anterior Tirar por incompatibilidade visual explícita com Create: Sulfuric Resonance. 2026-09-07 — decisão explícita do usuário substitui a anterior: MANTER Bits 'n Bobs e aceitar/monitorar o conflito visual com CSR. 2026-09-09 — runtime atualizado/revalidado em 2.3.2; CSR não está mais presente no snapshot físico, então o conflito permanece apenas como histórico, sem alterar a decisão Manter.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> ⚠️ Versão física confirmada: `bits_n_bobs-2.3.2.jar`, mod id `bits_n_bobs`, runtime `2.3.2`, com Create `6.0.10` no pack. O JAR contém **Sable Companion 1.4.2** em `META-INF/jarjar`; ele não é mod top-level. A decisão curatorial explícita **MANTER** permanece. O antigo conflito visual com Create: Sulfuric Resonance 0.4.1 é preservado como histórico, mas esse provider não está presente no snapshot físico atual.

## 1. Papel e autoridade
Create: Bits 'n' Bobs é um addon de **mecânica cinética, contraptions e decoração** para Create. Create continua authority de kinetic network, stress, speed, contraption assembly/movement e fluid infrastructure base. Bits 'n' Bobs registra peças e comportamentos adicionais que se conectam a esses sistemas.
Não criar uma segunda contabilidade de stress/rotation ou fluid transfer para “compatibilizar” o addon.

## 2. Colored fluid pipes e tanks
A documentação oficial confirma pipes/tanks tingíveis. Pipes de cores diferentes podem **ignorar-se entre si**, permitindo separar redes adjacentes visual e funcionalmente. O projeto cita extensão desse conceito a mais fluid components via Bits 'n' Dyes.
Riscos:
- conexão visual divergir da conexão real;
- recoloração enquanto há fluido produzir recomputação incorreta da rede;
- outro addon assumir que todo pipe Create adjacente conecta automaticamente.
Fluid amount/transfer continua authority do handler/Create/provider real.

## 3. Cogwheel Chain Drives
Cogwheel Chain Drives fornecem uma forma adicional de transferência cinética e são documentados como compatíveis com cogwheels do Create e vários addons.
Contrato:
- velocidade/rotação final deve entrar no network Create uma única vez;
- conexão em chain drive não pode duplicar source/consumer nodes;
- mudanças de bloco/chunk precisam provocar rebuild/invalidation do network conforme o provider.

## 4. Flanged Cogwheels
Flanged Cogwheels permitem formar Cogwheel Chain Drives com outros materiais, incluindo belts/ropes conforme documentação. Eles são parte da topologia cinética, não um sistema energético separado.
Testar direção/ratio real junto de shafts/cogwheels/chain drive e addons que também registram cogwheel variants.

## 5. Cogwheel Chain Carriage
O **Cogwheel Chain Carriage** permite anexar contraptions a um Chain Drive. Isso cruza dois lifecycles Create:
- kinetic network;
- contraption assembly/movement.
Assembly/disassembly precisa conservar blocks/state exatamente uma vez. Não deixar carriage ou contraption ghost após unload/restart.

## 6. Flywheel Bearing
O **Flywheel Bearing** gira suavemente uma contraption em função de sua massa. A documentação oficial também confirma opção de configuração para funcionar como **kinetic battery**.
Esse é um ponto de alto risco de integração:
- energia cinética armazenada deve ter uma única authority;
- carga/descarga não pode ocorrer duas vezes por tick externo;
- massa e rotação devem ser calculadas pelo provider vigente;
- save/reload precisa preservar apenas state persistente definido pelo addon.
Não reinterpretar o Flywheel Bearing como FE battery.

## 7. Chain Pulley
O **Chain Pulley** é variante do Rope Pulley usando chain. O movimento da contraption/blocos segue os contracts Create; Bits 'n' Bobs define a variante e assets/receitas correspondentes.
Validar limites, assemble/disassemble, chunk borders e restart com carga suspensa.

## 8. Encasings
O addon documenta:
- Piston Poles encasáveis;
- Industrial Iron como material de encasing;
- Weathered Iron como material de encasing.
Encasing muda apresentação/variante de bloco sem justificar duplicação de kinetic node. Resource packs e outros addons que alterem modelos precisam ser testados sobre o estado encased real.

## 9. Conteúdo decorativo documentado
### Struts
- Cable Struts;
- Girder Struts;
- Weathered Girder Struts.
São elementos point-to-point decorativos.

### Chairs
Cadeiras tingíveis/dinâmicas.

### Nixie
- Nixie Tube;
- Nixie Board;
com displays maiores e fonte ornamental própria.

### Tile palettes
Famílias de blocos tile para tipos de stone.

### Industrial set
- Industrial Truss;
- Grating Panel;
- Grating Block.
O truss também pode participar de encasing de pipes/shafts segundo a descrição oficial.

### Lighting
- Headlamps;
- Lightbulbs.
A documentação confirma múltiplos headlamps por bloco e tingimento individual.

### Weathered metal
- Weathered Metal Girder;
- Weathered Metal Bracket.
Completa o conjunto visual weathered metal.

## 10. Wood swapping de cogwheels
O projeto também adiciona troca/variação de madeira para cogwheels. É justamente nessa superfície de assets/modelos que foi documentada historicamente a incompatibilidade com Sulfuric Resonance; esse provider está ausente do snapshot atual.
Não tratar wood swap como mudança de ratio/kinetic behavior sem evidência específica.

## 11. Feature suppression e delta 2.3.2
Bits 'n' Bobs possui mecanismo de **feature suppression**, relevante em pack grande para desligar partes do addon quando necessário. A release 2.3.1 corrigiu um bug em que item suppression podia fazer creative tabs perderem vínculo com item groups.
A release física **2.3.2** acrescenta três correções oficiais:
- corrige Chain Drive sobrescrevendo comportamento de `gears n kinetics`;
- adiciona a mensagem de falha ausente ao tentar usar chain em outros tipos de cogwheel;
- restaura uma assinatura deprecated de `CogwheelChainRenderGeometryBuilder#renderChainSlowerButWithoutGaps` para evitar crash com Bits 'n Tracks.
No snapshot atual, Bits 'n Tracks não está top-level; portanto esse terceiro fix é capability upstream relevante, mas não prova uma integração ativa nesta instância.
Riscos:
- feature desabilitada ainda ter recipe/tag/model referenciado;
- creative tab divergir do registry real;
- datapack/KubeJS tentar criar recipe para item suprimido.

## 12. Dependência embarcada: Sable Companion
O JAR físico contém `sable-companion-common-1.21.1-1.4.2.jar` em `META-INF/jarjar`.
Regras do catálogo:
- não criar entrada top-level para esse arquivo;
- considerar o código embarcado em diagnóstico de classloading;
- não presumir que outros mods podem depender diretamente dessa cópia interna.

## 13. Histórico de conflito com Create: Sulfuric Resonance
A decisão curatorial anterior é preservada como histórico:
- Sulfuric Resonance 0.4.1 documentava incompatibilidade visual com **Thermochemical Cogwheels**;
- a causa registrada era a substituição/alteração de assets de cogwheel por Bits 'n' Bobs;
- em 07/09/2026, a decisão explícita do pack foi **MANTER Bits 'n' Bobs** e aceitar/monitorar esse conflito.
No snapshot físico atual de 08/09/2026, **Create: Sulfuric Resonance não está presente**. Portanto o conflito não é tratado como risco ativo desta instância. Se o provider voltar, o render-test específico deve ser reativado; a decisão histórica não é apagada.

## 14. Client/server
- kinetic network, fluid transfer, contraption state e feature registry são server/common;
- models, Nixie rendering, colors e outros assets são cliente;
- recoloração/GUI/input client precisa resultar em state validado no servidor;
- model override não pode alterar kinetic authority.

## 15. Lifecycle
Validar:
1. place/break de peças cinéticas;
2. chunk unload/reload;
3. server restart;
4. contraption assemble/disassemble;
5. Chain Carriage em movimento;
6. Flywheel Bearing charge/discharge e massa;
7. Chain Pulley com carga;
8. recoloração de pipes/tanks com fluidos presentes;
9. feature suppression + datapack/resource reload;
10. resource pack/model reload dos cogwheels.

## 16. Riscos
1. Double kinetic processing.
2. Kinetic battery duplicar energia/state após reload.
3. Contraption ghost/dupe via carriage/pulley.
4. Redes de fluidos de cores diferentes conectarem quando não deveriam.
5. Feature suppression deixar recipe/tag dangling.
6. Se Sulfuric Resonance for reintroduzido, o conflito visual histórico de cogwheel assets precisa ser revalidado; não é risco ativo no snapshot atual.
7. Resource pack sobrescrever cogwheel assets e ampliar incompatibilidade.
8. Sable Companion jarjar confundido com top-level.

## 17. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Colored pipe/tank: same color conecta, cores distintas seguem regra documentada.
3. Chain Drive/Flanged Cogwheel com rotações positivas/negativas e unload.
4. Chain Carriage assemble/disassemble/restart.
5. Flywheel Bearing normal e kinetic-battery config, sem duplicação.
6. Chain Pulley em chunk border.
7. Nixies/headlamps/chairs e outros blocks após resource reload.
8. Feature suppression: item suprimido não deixa creative tab/recipe quebrado.
9. Se Create: Sulfuric Resonance voltar ao pack, executar render-test específico dos Thermochemical Cogwheels; no snapshot atual esse provider está ausente.
10. Verificar ausência de entrada top-level Sable Companion no catálogo.

## 18. Evidência
- modlist física atual: Bits 'n' Bobs 2.3.2, Create 6.0.10 e Sable Companion 1.4.2 jarjar; Sulfuric Resonance e Bits 'n Tracks não estão top-level neste snapshot;
- CurseForge oficial do projeto e da release 2.3.2, incluindo os três fixes documentados da build;
- documentação oficial de colored pipes/tanks, chain drives, flanged cogwheels, chain carriage, Flywheel Bearing, Chain Pulley, encasings e conteúdo decorativo;
- decisão/histórico curatorial preexistente no Notion preservado integralmente quanto ao MANTER + conflito CSR aceito.

> ⚙️ Ownership canônico: Create = kinetic/fluid/contraption engine; Bits 'n' Bobs = extensões registradas. O conflito visual com Sulfuric Resonance permanece **preservado no histórico curatorial**, mas não é conflito ativo porque esse provider está ausente do snapshot atual.