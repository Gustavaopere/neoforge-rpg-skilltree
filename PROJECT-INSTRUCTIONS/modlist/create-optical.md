# Create Optical

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8176a56cdf5ad7cf0eaf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Optical
- **Arquivo JAR:** `create_optical-0.4.2.jar`
- **Versão 1.21.1:** 0.4.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Addon Create de transmissão óptica: converte rotação em beams e beams em rotação/redstone, modifica/combina luz, processa itens em belts e oferece display/leitura/modulação óptica.
- **Dependências:** Minecraft 1.21.1 + NeoForge; Create 6.0.10 físico. O projeto integra Ponder/JEI para documentação/recipes; a autoridade de execução permanece no servidor/recipe manager.
- **Sobreposição:** Integra-se à cinética, belts, redstone e recipe processing do Create. Não é duplicata de cabos/redstone: beams possuem propriedades/topologia próprias; revisar loops energéticos e rotas de processamento por comportamento real.
- **Compatibilidade/Riscos:** Riscos centrais: feedback beam→kinetic produzir ganho indevido; beams/state NBT stale após rotate/unload; Splitter/Condenser multiplicarem efeitos; Sensor gerar update storm; Beam Focuser processar item em duplicidade; regressão de recipes corrigida na 0.4.2; render divergir do servidor.
- **Observações:** JAR físico `create_optical-0.4.2.jar`, mod id `create_optical`, runtime 0.4.2. Branch oficial `1.21.1` declara MC 1.21.1/mod 0.4.2. Receptor é gerador cinético server-side com state persistido em NBT; Beam Focuser usa recipe óptica própria.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + publicação oficial Create Optical 0.4.2 + repositório oficial luccaPossamai/optical, branch matching `1.21.1`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-optical
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 0.4.2; grafo óptico, Source/Receptor/Sensor, componentes de beam, Beam Focuser, recipes, Ponder/JEI, persistência, lifecycle e riscos de feedback catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔦 **Identidade física confirmada:** `create_optical-0.4.2.jar`, mod id `create_optical`, runtime `0.4.2`, NeoForge 1.21.1. O branch oficial `1.21.1` declara Minecraft 1.21.1 e mod version 0.4.2, alinhado ao JAR físico.

## 1. Papel e authority
Create Optical adiciona uma camada de **transmissão óptica** ao ecossistema Create. O addon owns beams, propriedades ópticas, blocos emissores/receptores/sensores, processamento óptico e comunicação por luz. **Create 6.0.10** continua owner da rede cinética, stress, belts e infraestrutura de processamento sobre a qual o addon opera.

## 2. Modelo óptico
A arquitetura pública é baseada em beams emitidos por uma fonte, modificados ou redirecionados por componentes ópticos e consumidos por receptores/sensores/processadores. A topologia do beam é state operacional do addon; não deve ser reinterpretada por scripts externos como simples redstone ou simples rotação.

## 3. Optical Source
O **Optical Source** transforma condição cinética em emissão de beam. A documentação oficial afirma que a emissão depende da força rotacional aplicada. O source matching 0.4.2 confirma block entity própria e participação no grafo óptico.
Como Create owns a cinética de entrada, qualquer integração externa deve observar o state real do source em vez de fabricar beam por RPM presumido.

## 4. Optical Receptor
O **Optical Receptor** faz a conversão inversa: beam → rotação. O source 0.4.2 confirma que ele é um gerador cinético processado server-side e mantém estado de beams/sensors persistido em NBT.
Essa superfície exige atenção a feedback: Source→beam→Receptor→rotação não pode formar ganho energético ilimitado por combinação de configuração, múltiplos receptores ou loops geométricos.

## 5. Optical Sensor
O **Optical Sensor** produz redstone a partir das propriedades do beam. O servidor deve ser authority do valor lógico; render do beam não pode divergir da saída redstone. Loops beam↔redstone precisam convergir sem oscilação infinita ou update storm.

## 6. Mirror
O **Mirror** redireciona beams perpendicularmente conforme a geometria publicada. Rotação/orientação do bloco muda a rota óptica e deve invalidar/recalcular o caminho correspondente sem deixar beam fantasma após wrench, break ou chunk unload.

## 7. Polarizing Filter
O **Polarizing Filter** modifica/filtra a propriedade de polarização do beam. A polarização é parte da semântica do addon e participa de outros componentes; não reduzir o state a “beam presente/ausente” em integrações.

## 8. Beam Splitter Cube
O **Beam Splitter Cube** divide um beam e trabalha com polarização perpendicular. Splitting é ponto de balanceamento porque pode aumentar o número de rotas ativas; validar que a divisão não multiplica indevidamente energia/efeito quando os ramos chegam a receptores ou focusers.

## 9. Beam Condenser
O **Beam Condenser** combina até três beams/propriedades segundo a documentação oficial. Merge deve ser determinístico e recomputado quando qualquer entrada muda; remover uma entrada não pode deixar propriedades stale na saída.

## 10. Beam Focuser
O **Beam Focuser** executa processamento customizado de itens em belts controlado pelas características do beam. O source matching 0.4.2 confirma block entity própria e aplicação de recipe óptica em itens transportados.
A execução precisa ser **exactly-once**: um item não pode ser processado duas vezes por client prediction, retry de belt, múltiplos beams ou reload de recipe.

## 11. Recipe type e regressão 0.4.2
A linha 1.21.1 possui recipe type/config próprios para processamento óptico. O changelog oficial da **0.4.2** cita correção de “weird recipes behavior”, tornando recipe matching/consumo/output um regression gate obrigatório desta build.
JEI é apenas visualização. O servidor e o recipe manager carregado são authority do match e do output.

## 12. Thermal Optical Source
O projeto publica um **Thermal Optical Source** de maior capacidade associado a aplicações de fluido. Isso cria uma ponte concreta com fluid handling/heat do ecossistema Create. O addon owns a conversão óptica; o provider do fluido continua owner do conteúdo e Create continua owner da infraestrutura de fluidos.

## 13. Hologram Display
O **Hologram Display** exibe texto e itens. É uma superfície predominantemente client-facing, mas o conteúdo/state que determina o display deve convergir entre clientes. Não usar render holográfico como authority para automação ou permissões.

## 14. Beam Reader e Beam Modulator
O **Beam Reader** expõe propriedades do beam para inspeção. O **Beam Modulator** permite comunicação através da luz segundo a documentação pública. Essas superfícies ampliam o addon de energia/processamento para sinalização/telemetria; packet/state precisa permanecer consistente entre servidor e clientes.

## 15. Ponder e JEI
O projeto integra **Ponder** para explicar mecânicas ópticas e **JEI** para recipes. A modlist física atual mostra Ponder como dependência embarcada em componentes do ecossistema Create, não como entrada top-level deste lote. Ponder/JEI são documentação e descoberta; não são owners de recipe execution, beam state ou cinética.

## 16. Config, data e reload
O branch 1.21.1 possui configuração server-side e configuração de recipes própria. Mudanças de config/datapack e `/reload` precisam invalidar recipes e topologia derivada de forma segura; máquinas não podem continuar processando recipe removida ou beam state antigo.

## 17. Client/server, lifecycle e multiplayer
Beam rendering, hologram rendering e overlays são client-facing. Topologia óptica, receptor cinético, redstone, processamento e recipe consumption são server-authoritative.
Testar place→rotate→break, piston/contraption se suportado pelo runtime, chunk unload/reload, dimension/restart e dois clientes observando a mesma rede. Receptor/source/focuser não podem divergir entre jogadores.

## 18. Riscos
1. Loop Source→Receptor produz energia líquida indevida.
2. Splitter/Condenser multiplicam efeitos além da semântica prevista.
3. Beam fantasma persiste após break/rotate/unload.
4. Receptor perde ou duplica state cinético após restart.
5. Sensor gera update storm ou redstone loop.
6. Beam Focuser processa item duas vezes.
7. Regressão de recipe corrigida na 0.4.2 reaparece com datapacks/addons.
8. `/reload` deixa recipe ou beam cache stale.
9. Render de beam diverge do state server-side.
10. Redes grandes elevam custo de path recalculation/render.

## 19. Matriz de testes
- [ ] Dedicated server inicia com Create Optical 0.4.2 + Create 6.0.10.
- [ ] Optical Source emite apenas quando as condições cinéticas reais permitem.
- [ ] Optical Receptor gera rotação consistente com o beam e sem ganho infinito em loop.
- [ ] Mirror/Filter/Splitter/Condenser recalculam a rota sem beams fantasmas.
- [ ] Optical Sensor acompanha o state real sem update storm.
- [ ] Beam Focuser processa cada item exatamente uma vez.
- [ ] Recipes 0.4.2 não reproduzem o comportamento estranho corrigido no changelog.
- [ ] Chunk unload/reload preserva ou reconstrói corretamente a topologia.
- [ ] `/reload` atualiza recipes/config sem state stale.
- [ ] Dois clientes veem beam/hologram coerente com o servidor.
- [ ] Rede óptica grande não introduz regressão severa de tick/frame time.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física confirma JAR, mod id e runtime 0.4.2. A publicação oficial confirma NeoForge 1.21.1, Client & Server, componentes ópticos, JEI/Ponder e o changelog 0.4.2. O repositório oficial `luccaPossamai/optical`, branch **`1.21.1`**, declara exatamente Minecraft 1.21.1/mod 0.4.2 e confirma receptor cinético server-side, persistência NBT e Beam Focuser com recipe própria. Não foram inferidos IDs/classes não verificados além dessas superfícies.

> 🔒 **Boundary canônico:** Create owns a rede cinética/belts; Create Optical owns beams e processamento óptico. O resultado server-side real, não o render do beam, é authority para energia, redstone e recipes.
